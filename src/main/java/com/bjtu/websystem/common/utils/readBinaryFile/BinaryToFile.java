package com.bjtu.websystem.common.utils.readBinaryFile;

import com.bjtu.websystem.common.utils.GPS;
import com.bjtu.websystem.common.utils.GPS2Three;
import com.bjtu.websystem.model.readFileModels.*;
import com.csvreader.CsvReader;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @author Nekkl
 * @version 1.0
 * @description: 读取小车数据
 * @date 2021/3/31 20:52
 */
public class BinaryToFile {
	public static ReturnedCarInfoModel readData(String carInfoPath, String crossGridPath, String linkGridPath, GPS2Three converter) throws IOException, InterruptedException {
		System.out.println("读取数据中...");
		CountDownLatch countDownLatch = new CountDownLatch(2);
		// 读取crossGrids字典子线程
		ReadCrossGridInfo readCrossGridInfo = new ReadCrossGridInfo();
		readCrossGridInfo.setFilePath(crossGridPath);
		readCrossGridInfo.setConverter(converter);
		readCrossGridInfo.setCountDownLatch(countDownLatch);
		readCrossGridInfo.start();
//		CrossGrid[] crossGrids = readCrossGridInfo(crossGridPath, converter);

		// 读取linkGrids字典子线程
		ReadLinkGridInfo readLinkGridInfo = new ReadLinkGridInfo();
		readLinkGridInfo.setFilePath(linkGridPath);
		readLinkGridInfo.setConverter(converter);
		readLinkGridInfo.setCountDownLatch(countDownLatch);
		readLinkGridInfo.start();

		// 主线程取数据
		// 阻塞主线程，直到子线程执行结束
		countDownLatch.await();
		CrossGrid[] crossGrids = readCrossGridInfo.getCrossGrids();
		LinkGrid[] linkGrids = readLinkGridInfo.getLinkGrids();
//		LinkGrid[] linkGrids = readLinkGridInfo(linkGridPath, converter);

		// 创建 gridGPSHashMap 对象 存放 linkGrids 和 carInfo的映射
		HashMap<Grid, GPS> gridGPSHashMap = new HashMap<Grid, GPS>(linkGrids.length);
		for (int i = 0; i < linkGrids.length; ++i) {
			gridGPSHashMap.put(new Grid(
							linkGrids[i].getOriginCross(),
							linkGrids[i].getDestinationCross(),
							linkGrids[i].getGridNumber()
					),
					new GPS(
							linkGrids[i].getLongitude(),
							linkGrids[i].getLatitude()
					)
			);
		}


		// 读取小车数据，生成路径,传入两个映射后的字典
		CarInfoModel carInfoModel = readCarInfo(carInfoPath, gridGPSHashMap, crossGrids);
        HashMap<Integer, Trail> carInfoHashMap = carInfoModel.getCarInfoHashMap();

        //创建小车运动数组 存放所有小车运动信息
		Geometry[] geometries = new Geometry[carInfoHashMap.size()];

		// 遍历 carInfoHashMap,将所有小车运动信息存入 geometries当中
		Map map = carInfoHashMap;
		Iterator iter = map.entrySet().iterator();
		int i = 0;
		int count = 0;
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Trail trail = (Trail) entry.getValue();

			count += trail.getCoordinates().size();

			// 存放一个小车轨迹信息的坐标和时间序列
			double[][] coordinates = new double[trail.getCoordinates().size()][3];
			Integer[] times = new Integer[trail.getTimes().size()];

			for (int j = 0 ;j < trail.getTimes().size(); ++j){
				coordinates[j] = trail.getCoordinates().get(j).getThreePos();
				times[j] = trail.getTimes().get(j);
			}

			Geometry geometry = new Geometry(trail.getStartTime(),trail.getEndTime(),trail.getCarType(), coordinates,times);
			geometries[i++] = geometry;
		}

		ReturnedCarInfoModel returnedCarInfoModel = new ReturnedCarInfoModel(geometries, carInfoModel.getETime(), carInfoModel.getLTime());
		System.out.println("数据总条数为：" + count);
		System.out.println("往前端发送数据");
		return returnedCarInfoModel;
	}

	/**
	 * @description: 读取carInfo数据
	 * @author Nekkl
	 * @date: 2021/3/31 21:03
	 * @param: [filePath]
	 * @return: HashMap<Integer, CarInfo> 将carInfo存入carNum对应的哈希表中的位置
	 */
	public static CarInfoModel readCarInfo(String filePath, HashMap<Grid, GPS> gridGPSHashMap, CrossGrid[] crossGrids ) {
	    // 存放所有路径的HashMap
        HashMap<Integer, Trail> trailsHashMap = new HashMap<Integer, Trail>();
        // 最初时间
        int eTime = 1000000;
        // 完全结束时间
        int lTime = -1;
        // carInfoModel
		CarInfoModel carInfoModel = new CarInfoModel(trailsHashMap, eTime, lTime);

		try {

			// 打开文件流
			FileInputStream fis = new FileInputStream(new File(filePath));
			DataInputStream dis = new DataInputStream(fis);

			// 获取记录条数
			int itemLength = 4 * 8;
			int numOfRecords = dis.available() / itemLength;

			//读取小车数据
			System.out.println("数据条数据: " + numOfRecords);
			for (int i = 0; i < numOfRecords; ++i) {

				CarInfo carInfoTemp = new CarInfo(
						C2J_Int(dis.readInt()),
						C2J_Int(dis.readInt()),
						C2J_Int(dis.readInt()),
						C2J_Int(dis.readInt()),
						C2J_Int(dis.readInt()),
						C2J_Int(dis.readInt()),
						C2J_Int(dis.readInt()),
						C2J_Int(dis.readInt())
				);

				Trail trail = null;

				// 根据编号获取路径
				if((trail = trailsHashMap.get(carInfoTemp.getCarNum()) )== null){

                    // // 如果此路径还未创建,则根据小车编号创建一条新路径，存放此路径内容的HashMap
                    trail = new Trail(new LinkedList<Coordinate>(),new LinkedList<Integer>(),carInfoTemp.getCarType(), 0, 0);
                    trailsHashMap.put(carInfoTemp.getCarNum(), trail);

                }

				// 获取路径的LinkedList
				LinkedList<Coordinate> coordinates = trail.getCoordinates();
				// 获取路径的时间序列 times
				LinkedList<Integer> times = trail.getTimes();
                // 判断CrossType, 确定查找坐标的字典
                if (carInfoTemp.getCrossType() == 0){


                    double[] threePos = new double[]{
							// x
							crossGrids[carInfoTemp.getDCrossNum()].getLongitude(),
							// y
                            crossGrids[carInfoTemp.getDCrossNum()].getLatitude(),
							// z
							0
                    };
                    Coordinate coordinate = new Coordinate(threePos);

                    // 加入坐标序列
					coordinates.add(
                            coordinate
                    );
					// 加入时间序列
					times.add(
							(
									carInfoTemp.getGlobalTime()
							)
					);
                }else {

					/**
					* 坐标格式threePos = {x, y, z} z 默认为0
					*/
                    double[] threePos = new double[]{
							gridGPSHashMap.get(
									new Grid(
											carInfoTemp.getOCrossNum(),
											carInfoTemp.getDCrossNum(),
											carInfoTemp.getGrid()
									)
							).getLon(),
                            gridGPSHashMap.get(
                                    new Grid(
                                    carInfoTemp.getOCrossNum(),
                                    carInfoTemp.getDCrossNum(),
                                    carInfoTemp.getGrid()
                                    )
                            ).getLat(),
							3.5
                    };
					Coordinate coordinate = new Coordinate(threePos);

					// 加入坐标序列
					coordinates.add(
							coordinate
					);
					// 加入时间序列
					times.add(
							(
									carInfoTemp.getGlobalTime()
							)
					);

                }
                // 如果当前节点为路径的第一个节点，则将这个节点的时刻作为路径（轨迹）的开始时刻
                if (coordinates.size() == 1){
                	int startTime = carInfoTemp.getGlobalTime();
                    trail.setStartTime(startTime);
                    if (startTime < carInfoModel.getETime()){
                    	carInfoModel.setETime(startTime);
					}
				}

				// 不断更新路径的结束时刻，最后一个读入的节点的时刻则为路径的结束时刻
				int endTime = carInfoTemp.getGlobalTime();
				trail.setEndTime(endTime);
				if(endTime > carInfoModel.getLTime()){
					carInfoModel.setLTime(endTime);
				}

			}

			dis.close();
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return carInfoModel;
	}

	public static CrossGrid[] readCrossGridInfo(String filePath, GPS2Three converter) throws IOException {
		CrossGrid[] crossGrids = null;

		// 获取记录条数（行数）
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String tmpStr = "";
		int rowCount = 0;
		while (br.readLine() != null) {
			rowCount++;
		}
		crossGrids = new CrossGrid[rowCount];
		int j = 0;
		//CSV文件阅读器
		CsvReader reader = new CsvReader(filePath, ',', Charset.forName("gbk"));
		GPS gps = new GPS(0,0);

		// 整个while就是为了组装成为 插入语句的形式
		while (reader.readRecord()) {

			// 读取坐标
			double longitude = Double.valueOf(reader.get(1));
			double latitude = Double.valueOf(reader.get(2));

			// 坐标转换成Three.js坐标
			double[] lonLat = converter.GPS2Pixel(longitude, latitude);

			// 生成一个坐标对象
			crossGrids[j++] = new CrossGrid(
					(int) Float.parseFloat(reader.get(0)),
					lonLat[0],
					lonLat[1]
			);
		}
		return crossGrids;
	}

	public static LinkGrid[] readLinkGridInfo(String filePath, GPS2Three converter) throws IOException {
		LinkGrid[] linkGrids = null;

		// 获取记录条数（行数）
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String tmpStr = "";
		int rowCount = 0;
		while (br.readLine() != null) {
			rowCount++;
		}
		linkGrids = new LinkGrid[rowCount];
		int j = 0;
		//CSV文件阅读器
		CsvReader reader = new CsvReader(filePath, ',', Charset.forName("gbk"));
		GPS gps = new GPS(1,1);
		// 整个while就是为了组装成为 插入语句的形式5
		while (reader.readRecord()) {

			// 读取坐标
			double longitude = Double.valueOf(reader.get(3));
			double latitude = Double.valueOf(reader.get(4));

			// 坐标转换成Three.js坐标
			double[] lonLat = converter.GPS2Pixel(longitude, latitude);

			linkGrids[j++] = new LinkGrid(
					(int) Float.parseFloat(reader.get(0)),
					(int) Float.parseFloat(reader.get(1)),
					(int) Float.parseFloat(reader.get(2)),
					lonLat[0],
					lonLat[1]
			);
		}
		return linkGrids;
	}

	public static int C2J_Int(int num) {

		int value = 0;
		int i0 = num >> 24 & 0xff;
		int i1 = (num >> 8) & 0xff00;
		int i2 = (num << 8) & 0xff0000;
		int i3 = (num << 24) & 0xff000000;

		value = i0 | i1 | i2 | i3;
		return value;
	}

	public static short C2J_Short(short num) {
		int value = 0;
		int i0 = (num >> 8) & 0xff;
		int i1 = (num << 8) & 0xff00;
		value = i0 | i1;
		return (short) value;
	}

	public static String C2J_String(DataInputStream dis) {
		String result = "";
		byte buffer[] = null;
		try {
			//读取字符串长度
			int size = C2J_Int(dis.readInt());

			buffer = new byte[size];
			dis.read(buffer);
			result = new String(buffer, "gb2312");
			System.out.println("result: " + result);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

//	public static void main(String[] args) throws IOException {
//		String fileName = "C:\\Users\\Nekkl\\Desktop\\carInfo.txt";
//		readData();
//	}
}

