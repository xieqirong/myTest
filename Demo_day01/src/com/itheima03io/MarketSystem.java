package com.itheima03io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import jdk.internal.jfr.events.FileWriteEvent;

/*
 * 	超市小票分析(功能分析):
		1.主框架(主要步骤):
			主界面
			数据准备工作
		     	定义一个商品类:id等信息
				初始化商品信息:创建一个货架集合存储所有商品对象
			a.购买商品功能
				步骤...
			b.结算并打印功能
			c.退出
		2.加入io流
 * */
public class MarketSystem {
	// 创建货架集合,存储所有商品数据
	static ArrayList<GoodsItem> list = new ArrayList<>();
	// 创建Scanner录入对象
	static Scanner sc = new Scanner(System.in);
	// 创建购物车集合存储用户需要购买的商品信息
	static ArrayList<GoodsItem> shoppingCar = new ArrayList<>();

	//主方法
	public static void main(String[] args) throws IOException {
		// 主界面
		System.out.println("\t欢迎进入超市购物系统...");
		// 初始化商品信息 保存在货架集合中(调用方法)
		addGoodsItemToArrayList();
		//读取购物车中是否有没有结算的商品数据
		breadshoppingCarDatd();

		// 循环操作主干功能,知道选择退出系统,结束循环.
		while (true) {
			System.out.println("请输入操作:");
			System.out.println("1.购买商品\t2.打印结算\t3.退出系统");
			System.out.println(".......................");
			// 接收录入对象
			String inputNumber = sc.next();
			// 创建选择操作,实现对应的功能主干
			switch (inputNumber) {
			case "1":
				// 进行购买操作,通过方法实现
				buyGoodsItem();
				break;
			case "2":
				//打印小票
				printTicket();
				break;
			case "3":
				System.out.println("退出系统");
				System.exit(0);
				break;

			default:
				System.out.println("输入的数字有误,请重新输入");
				break;
			}
		}
	}

	/*
	 * 读取购物车文件中的数据,看是否还有未结算的商品存在
	 * */
	private static void breadshoppingCarDatd() throws FileNotFoundException, IOException {
		//读取购物车中是否有保留商品数据,创建输入缓冲流
		BufferedReader br = new BufferedReader(new FileReader("shoppingCar.txt"));
		//定义一个字符串变量进行接收
		String line;
		//循环读取一行
		while ((line=br.readLine())!=null) {
			//切割读取到的每一行数据
			String[] str = line.split("-");
			//创建购物车对象,并给成员变量赋值
			GoodsItem item = new GoodsItem(str[0],str[1],Double.parseDouble(str[2]),
					Integer.parseInt(str[3]),str[4]);
			//保存对象到购物车集合中
			shoppingCar.add(item);
		}
		br.close();
	}

	/*
	 * 结算和打印票据
	 */
	private static void printTicket() throws IOException {
		// 判断购物车是否有购买的商品
		if (shoppingCar.size() == 0) {
			System.out.println("还没有购买商品,请进购买操作");
			return;
		}
		// 打印票头
		System.out.println("\t欢迎光临");
		System.out.println("商品编号\t名称\t单价\t数量\t单位\t金额");
		System.out.println(".........................");
		// 定义一个变量计算商品总件数
		int totalCount = 0;
		// 定义一个变量计算总金额
		double totalMoney = 0;
		// 打印票体
		// 循环遍历购物车集合,打印商品信息
		for (int index = 0; index < shoppingCar.size(); index++) {
			GoodsItem item = shoppingCar.get(index);
			double money = item.getPrice() * item.getBuyCount();
			totalMoney += money;
			totalCount += item.getBuyCount();
			System.out.println(item.getId() + "\t" + item.getName() + "\t" + item.getPrice() + "\t" + item.getBuyCount()
					+ "\t" + item.getUnit() + "\t" + money);
		}
		System.out.println(".........................");
		// 打印票尾
		System.out.println("共计" + shoppingCar.size() + "项商品");
		System.out.println(totalCount + "件");
		System.out.println(totalMoney + "元");
		System.out.println(".........................");

		// 结算完成后,清空购物车
		shoppingCar.clear();
		//结算后,同时清空文件内容
		FileWriter fw = new FileWriter("shoppingCar.txt");
		fw.close();
	}

	/*
	 * 购买操作
	 */
	private static void buyGoodsItem() throws IOException {

		// 商品展示开头格式
		System.out.println("商品列表:");
		System.out.println("商品编号\t名称\t价格\t单位");
		// 遍历集合,实现货架商品的展示
		for (int index = 0; index < list.size(); index++) {
			GoodsItem item = list.get(index);
			System.out.println(item.getId() + "\t" + item.getName() + "\t" + item.getPrice() + "\t" + item.getUnit());
		}
		System.out.println(".......................");

		System.out.println("请按照指定格式输入要购买的商品,id-数量,例如001-7,输入end结束购买");
		// 创建循环录入
		while (true) {
			// 接收用户键入的数据
			String buyInput = sc.next();
			// 判断输入是否为end
			if (buyInput.equals("end")) {
				System.out.println("结束购买");
				return;
			}
			// 不为end 则将输入字符串用"-"分割
			String[] strs = buyInput.split("-");
			// 判断分割后的数组长度是否为2
			if (strs.length != 2) {
				System.out.println("输入不合法,请重新输入");
				continue;
			}
			// 为2,则得到還未判断的Id,和需要购买的数量
			String inputId = strs[0];
			int buyCount = Integer.parseInt(strs[1]);
			// 判断id是否存在,调用方法,货架集合中id和输入Id做比较,存在相同id,返回对应对象,否则返回空值
			GoodsItem item = existIputIdAtList(inputId);
			// 判断返回的Item是否为空
			if (item == null) {
				System.out.println("输入id不存在,请重新输入");
				continue;
			}
			// 返回的Item不为空时,复制这个要购买的商品对象的属性,生成一个newItem,同时多添加一个buyCount属性
			GoodsItem newItem = new GoodsItem(item.getId(), item.getName(), item.getPrice(), buyCount, item.getUnit());

			// 创建一个购物车集合,用来存储需要购买的商品newItem //生成方法
			addnewItemToShoppingCar(newItem);
			//创建方法,保存购物车中未结算商品数据到文件
			bwShoppingCarDataToTxt();

		}
	}

	/*
	 * 保存购物车中未结算数据到文本
	 * */
	private static void bwShoppingCarDataToTxt() throws IOException {
		// 每次添加完商品到购物车之后,保存购物车数据到文本中,创建输出缓冲流.
		BufferedWriter bw = new BufferedWriter(new FileWriter("shoppingCar.txt"));
		// 遍历购物车数据,写出到文件
		for (int index = 0; index < shoppingCar.size(); index++) {
			GoodsItem item2 = shoppingCar.get(index);
			// 创建StringBuilder 按照一定格式添加对象的数据
			StringBuilder sb = new StringBuilder();
			sb.append(item2.getId() + "-");
			sb.append(item2.getName() + "-");
			sb.append(item2.getPrice() + "-");
			sb.append(item2.getBuyCount() + "-");
			sb.append(item2.getUnit());
			// 写入文本
			bw.write(sb.toString());
			bw.newLine();
			bw.flush();
		}
		bw.close();
		
	}

	/*
	 * 创建一个购物车集合,用来存储需要购买的商品newItem
	 */
	private static void addnewItemToShoppingCar(GoodsItem newItem) {

		// 判断购物车集合中是否已经存在newItem相同id的商品 遍历购物车集合
		for (int index = 0; index < shoppingCar.size(); index++) {
			GoodsItem item = shoppingCar.get(index);
			if (newItem.getId().equals(item.getId())) {
				item.setBuyCount(item.getBuyCount() + newItem.getBuyCount());
				return;
			}
		}
		// 购物车集合中不存在newItem相同id的商品,则直接添加商品到购物车集合
		shoppingCar.add(newItem);

	}

	/*
	 * 判断输入的id是否存在对应的商品编号
	 * */
	private static GoodsItem existIputIdAtList(String inputId) {

		// 遍历集合,判断输入的商品id是否存在在货架集合中,存在则返回对应的商品对象
		for (int index = 0; index < list.size(); index++) {
			GoodsItem item = list.get(index);
			if (inputId.equals(item.getId())) {
				return item;
			}
		}
		// 不存在,就返回空值
		return null;

	}

	/*
	 * 初始化商品,到货架集合
	 */
	private static void addGoodsItemToArrayList() {

		// 创建商品对象,添加到货架集合中
		list.add(new GoodsItem("001", "核桃", 25, "斤"));
		list.add(new GoodsItem("002", "牛奶", 35, "升"));
		list.add(new GoodsItem("003", "U盘", 88, "个"));
		list.add(new GoodsItem("004", "苍井空", 9999, "个"));

	}

}
