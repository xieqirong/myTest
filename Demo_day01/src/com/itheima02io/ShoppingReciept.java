package com.itheima02io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ShoppingReciept {
	//创建集合存放所有商品项
	static ArrayList<GoodsItem> items = new ArrayList<>();
	//创建从购物车集合
	static ArrayList<GoodsItem> shoppingCar = new ArrayList<>();
	//創建鍵盤錄入系統
	static Scanner sc = new Scanner(System.in);
	
	//主方法
	public static void main(String[] args) throws IOException {
		
		//初始化商品信息(调用方法)
		initGoodsItem();
		//显示主界面
		System.out.println("\t欢迎使用超市购物系统");
		//读取购物车文件中数据
		breadingtoshopingCartxt();

		//循環使用購物系統
		while(true){
		System.out.println("请输入你要进行的操作");
		System.out.println("1.购买商品          2.结算并打印小票       3.退出系统");
		//接收用户的操作数字
		String optNumber = sc.next();
		//使用switch匹配用户输入的数字
		switch (optNumber) {
		case "1":
			//"购买商品";
			buyGoodsItem();
			break;
		case "2":
			//"结算并打印小票";
			printTicket();
			break;
		case "3":
			//退出购物系统
			System.out.println("感谢使用购物系统,欢迎下次光临");
			System.exit(0);
			break;

		default:
			System.out.println("你输入的操作数有误");
			break;
		}
				
		}
	}
	
	private static void breadingtoshopingCartxt() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("shoppingCar.txt"));
		//读取文件中信息,一行读取
		String line;
		while((line=br.readLine())!=null){
			//切割字符串
			String[] spl = line.split("-");
			//001-少林核桃-15.5-斤-10
			//创建一个商品对象
			GoodsItem item = new GoodsItem(spl[0],spl[1],Double.parseDouble(spl[2]),
					spl[4],Integer.parseInt(spl[3]));
			shoppingCar.add(item);
		}
		//关闭资源
		br.close();
	}

	/*
	 * 结算并打印小票
	 * 
	 * */
	private static void printTicket() throws IOException {
		/*
		 * 判断购物车中是否有商品,有就遍历购物车集合,统计价格数量,结算打印
		 * 没有就提示用户先购买后再结算
		 * */
		if (shoppingCar.size()==0) {
			//不存在时
			System.out.println("你还没有购买商品,请先购买商品再结算");
			return;
		}
		//购物车存在商品需要结算时
		//输出票头
		System.out.println("....................");
		System.out.println("\t欢迎光临");
		System.out.println("名称\t售价\t数量\t金额");
		System.out.println("....................");
		
		//定义变量记录购买了多少件商品
		int totalCount = 0;
		//定义变量记录所有商品的总金额
		double totalMoney = 0;
		//输出票体
		//遍历购物车集合
		for (int index = 0; index < shoppingCar.size(); index++) {
			GoodsItem item = shoppingCar.get(index);
			//计算对应商品的金额
			double totalPrice = item.getPrice()*item.getBuyCount();
			//累加商品数量
			totalCount += item.getBuyCount();
			//累加商品金额,得到总金额
			totalMoney += totalPrice;
			System.out.println(item.getName()+"\t"+item.getPrice()+
					"\t"+item.getBuyCount()+"\t"+totalPrice);
		}
		System.out.println(".......................");
		
		//输出票尾
		System.out.println(shoppingCar.size()+"项商品");
		System.out.println("共计"+totalCount+"件");
		System.out.println("共"+totalMoney+"元");
		System.out.println(".......................");
		
		//结算后,清空购物车
		shoppingCar.clear();
		//shoppingCar=null;//(不能把购物车砸了)
		//清空文件信息
		FileWriter fw = new FileWriter("shoppingCar.txt");
		fw.close();
	}
	/**
	 * 购买商品
	 * @throws IOException 
	 * */
	private static void buyGoodsItem() throws IOException {
		//展示所有的商品
		System.out.println(".......................");
		System.out.println("\t商品列表:");
		System.out.println("商品id\t名称\t单价\t计价单位");
		//遍历集合
		for (int index = 0; index < items.size(); index++) {
			GoodsItem item = items.get(index);
			//输出商品信息
			System.out.println(item.getId()+"\t"+item.getName()+"\t"
			+item.getPrice()+"\t"+item.getUnit());
		}
		System.out.println(".......................");
		//提示客户输入购买信息
		System.out.println("请输入您要购买的商品项(格式:id-购买数量),输入endb表示购买结束");
		//使用循环购买
		while (true) {
			//接受用户输入的购买信息
			String input = sc.next();
			//判断是否是结束购买
			if(input.equals("end")){
				System.out.println("结束购买");
				break;
			}
			//使用  - 切割信息字符串
			String[] strs = input.split("-");
			//判断用户输入的格式是否正确
			if (strs.length!=2) {
				System.out.println("你输入的姿势不对,请换个姿势输入...");
				continue;
			}
			//获得商品id
			String id = strs[0];//strs[1];??
			//判断用户输入的id是否存在
			GoodsItem item = findGoodsItemById(id);
			
			if(item==null){
				//商品不存在
				System.out.println("你购买的商品不存在,请重新输入");
				continue;
			}
			
			//id对应得商品存在,则创建新的商品项:新的商品项属性和对应得货架上(items集合中)的item对象的属性完全相同,同时加上购买的数量的属性
			GoodsItem newItem = new GoodsItem(item.getId(),item.getName(),item.getPrice(),
					item.getUnit(),Integer.parseInt(strs[1]));
			//(存在时),然后将商品存放在购物车集合中,调用方法
			addGoodsItemToShoppingCar(newItem);
			
			//将购物车中信息,写出到文件,调用方法.
			writingToText();
		}
		

	}
	
	/*
	 * 创建购物车 添加商品项
	 * */
	
	private static void addGoodsItemToShoppingCar(GoodsItem newItem) throws IOException {
		//判断商品是否存在在购物车中,先遍历购物车集合
		for (int index = 0; index < shoppingCar.size(); index++) {
			GoodsItem item = shoppingCar.get(index);
			//判断是否存在
			if(item.getId().equals(newItem.getId())){
				//本身就   存在购物车中,只需要改变原来的购买数量  用对象的set方法重新设置
				item.setBuyCount(item.getBuyCount()+newItem.getBuyCount());
				return;
			}
		}
		
		//是新的  则要添加的商品,则将商品项添加到购物车中
		shoppingCar.add(newItem);

		
	}
	
	/*
	 * 将购物车中信息,写出到文件
	 * */
	private static void writingToText() throws IOException {
		//创建输出流
		BufferedWriter bw = new BufferedWriter(new FileWriter("shoppingCar.txt"));
		//循环写出shoppingCar中数据
		for (int index = 0; index < shoppingCar.size(); index++) {
			//得到对应对象
			GoodsItem item = shoppingCar.get(index);
			//按照一定格式写入,加"-",引用StringBulider
			StringBuilder sb = new StringBuilder();
			sb.append(item.getId()+"-");
			sb.append(item.getName()+"-");
			sb.append(item.getPrice()+"-");
			sb.append(item.getBuyCount()+"-");
			sb.append(item.getUnit());
			bw.write(sb.toString());
			bw.newLine();
			bw.flush();
			
		}
		
		//关闭流
		bw.close();
		
	}

	/*
	 * 根据id查找商品项
	 * */
	private static GoodsItem findGoodsItemById(String id) {
		//遍历商品集合
		for (int index = 0; index <items.size(); index++) {
			//获得引索对应得商品项
			GoodsItem item = items.get(index);
			//判断商品项得id与输入的id是否相同
			if (item.getId().equals(id)) {
				//存在返回对应对象
				return item;
			}
		}
	
		//不存在返回 空
		return null;
	}
	/**
	 * 初始化商品信息
	 */

	private static void initGoodsItem() {
	
		//创建商品项添加到集合中
		items.add(new GoodsItem("001","少林核桃",15.5,"斤"));
		items.add(new GoodsItem("002","尚康饼干",14.5,"包"));
		items.add(new GoodsItem("003","移动硬盘",345.0,"个"));
		items.add(new GoodsItem("004","高清无码",199.0,"G"));
		
	}

}
