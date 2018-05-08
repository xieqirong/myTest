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
 * 	����СƱ����(���ܷ���):
		1.�����(��Ҫ����):
			������
			����׼������
		     	����һ����Ʒ��:id����Ϣ
				��ʼ����Ʒ��Ϣ:����һ�����ܼ��ϴ洢������Ʒ����
			a.������Ʒ����
				����...
			b.���㲢��ӡ����
			c.�˳�
		2.����io��
 * */
public class MarketSystem {
	// �������ܼ���,�洢������Ʒ����
	static ArrayList<GoodsItem> list = new ArrayList<>();
	// ����Scanner¼�����
	static Scanner sc = new Scanner(System.in);
	// �������ﳵ���ϴ洢�û���Ҫ�������Ʒ��Ϣ
	static ArrayList<GoodsItem> shoppingCar = new ArrayList<>();

	//������
	public static void main(String[] args) throws IOException {
		// ������
		System.out.println("\t��ӭ���볬�й���ϵͳ...");
		// ��ʼ����Ʒ��Ϣ �����ڻ��ܼ�����(���÷���)
		addGoodsItemToArrayList();
		//��ȡ���ﳵ���Ƿ���û�н������Ʒ����
		breadshoppingCarDatd();

		// ѭ���������ɹ���,֪��ѡ���˳�ϵͳ,����ѭ��.
		while (true) {
			System.out.println("���������:");
			System.out.println("1.������Ʒ\t2.��ӡ����\t3.�˳�ϵͳ");
			System.out.println(".......................");
			// ����¼�����
			String inputNumber = sc.next();
			// ����ѡ�����,ʵ�ֶ�Ӧ�Ĺ�������
			switch (inputNumber) {
			case "1":
				// ���й������,ͨ������ʵ��
				buyGoodsItem();
				break;
			case "2":
				//��ӡСƱ
				printTicket();
				break;
			case "3":
				System.out.println("�˳�ϵͳ");
				System.exit(0);
				break;

			default:
				System.out.println("�������������,����������");
				break;
			}
		}
	}

	/*
	 * ��ȡ���ﳵ�ļ��е�����,���Ƿ���δ�������Ʒ����
	 * */
	private static void breadshoppingCarDatd() throws FileNotFoundException, IOException {
		//��ȡ���ﳵ���Ƿ��б�����Ʒ����,�������뻺����
		BufferedReader br = new BufferedReader(new FileReader("shoppingCar.txt"));
		//����һ���ַ����������н���
		String line;
		//ѭ����ȡһ��
		while ((line=br.readLine())!=null) {
			//�и��ȡ����ÿһ������
			String[] str = line.split("-");
			//�������ﳵ����,������Ա������ֵ
			GoodsItem item = new GoodsItem(str[0],str[1],Double.parseDouble(str[2]),
					Integer.parseInt(str[3]),str[4]);
			//������󵽹��ﳵ������
			shoppingCar.add(item);
		}
		br.close();
	}

	/*
	 * ����ʹ�ӡƱ��
	 */
	private static void printTicket() throws IOException {
		// �жϹ��ﳵ�Ƿ��й������Ʒ
		if (shoppingCar.size() == 0) {
			System.out.println("��û�й�����Ʒ,����������");
			return;
		}
		// ��ӡƱͷ
		System.out.println("\t��ӭ����");
		System.out.println("��Ʒ���\t����\t����\t����\t��λ\t���");
		System.out.println(".........................");
		// ����һ������������Ʒ�ܼ���
		int totalCount = 0;
		// ����һ�����������ܽ��
		double totalMoney = 0;
		// ��ӡƱ��
		// ѭ���������ﳵ����,��ӡ��Ʒ��Ϣ
		for (int index = 0; index < shoppingCar.size(); index++) {
			GoodsItem item = shoppingCar.get(index);
			double money = item.getPrice() * item.getBuyCount();
			totalMoney += money;
			totalCount += item.getBuyCount();
			System.out.println(item.getId() + "\t" + item.getName() + "\t" + item.getPrice() + "\t" + item.getBuyCount()
					+ "\t" + item.getUnit() + "\t" + money);
		}
		System.out.println(".........................");
		// ��ӡƱβ
		System.out.println("����" + shoppingCar.size() + "����Ʒ");
		System.out.println(totalCount + "��");
		System.out.println(totalMoney + "Ԫ");
		System.out.println(".........................");

		// ������ɺ�,��չ��ﳵ
		shoppingCar.clear();
		//�����,ͬʱ����ļ�����
		FileWriter fw = new FileWriter("shoppingCar.txt");
		fw.close();
	}

	/*
	 * �������
	 */
	private static void buyGoodsItem() throws IOException {

		// ��Ʒչʾ��ͷ��ʽ
		System.out.println("��Ʒ�б�:");
		System.out.println("��Ʒ���\t����\t�۸�\t��λ");
		// ��������,ʵ�ֻ�����Ʒ��չʾ
		for (int index = 0; index < list.size(); index++) {
			GoodsItem item = list.get(index);
			System.out.println(item.getId() + "\t" + item.getName() + "\t" + item.getPrice() + "\t" + item.getUnit());
		}
		System.out.println(".......................");

		System.out.println("�밴��ָ����ʽ����Ҫ�������Ʒ,id-����,����001-7,����end��������");
		// ����ѭ��¼��
		while (true) {
			// �����û����������
			String buyInput = sc.next();
			// �ж������Ƿ�Ϊend
			if (buyInput.equals("end")) {
				System.out.println("��������");
				return;
			}
			// ��Ϊend �������ַ�����"-"�ָ�
			String[] strs = buyInput.split("-");
			// �жϷָ������鳤���Ƿ�Ϊ2
			if (strs.length != 2) {
				System.out.println("���벻�Ϸ�,����������");
				continue;
			}
			// Ϊ2,��õ�߀δ�жϵ�Id,����Ҫ���������
			String inputId = strs[0];
			int buyCount = Integer.parseInt(strs[1]);
			// �ж�id�Ƿ����,���÷���,���ܼ�����id������Id���Ƚ�,������ͬid,���ض�Ӧ����,���򷵻ؿ�ֵ
			GoodsItem item = existIputIdAtList(inputId);
			// �жϷ��ص�Item�Ƿ�Ϊ��
			if (item == null) {
				System.out.println("����id������,����������");
				continue;
			}
			// ���ص�Item��Ϊ��ʱ,�������Ҫ�������Ʒ���������,����һ��newItem,ͬʱ�����һ��buyCount����
			GoodsItem newItem = new GoodsItem(item.getId(), item.getName(), item.getPrice(), buyCount, item.getUnit());

			// ����һ�����ﳵ����,�����洢��Ҫ�������ƷnewItem //���ɷ���
			addnewItemToShoppingCar(newItem);
			//��������,���湺�ﳵ��δ������Ʒ���ݵ��ļ�
			bwShoppingCarDataToTxt();

		}
	}

	/*
	 * ���湺�ﳵ��δ�������ݵ��ı�
	 * */
	private static void bwShoppingCarDataToTxt() throws IOException {
		// ÿ���������Ʒ�����ﳵ֮��,���湺�ﳵ���ݵ��ı���,�������������.
		BufferedWriter bw = new BufferedWriter(new FileWriter("shoppingCar.txt"));
		// �������ﳵ����,д�����ļ�
		for (int index = 0; index < shoppingCar.size(); index++) {
			GoodsItem item2 = shoppingCar.get(index);
			// ����StringBuilder ����һ����ʽ��Ӷ��������
			StringBuilder sb = new StringBuilder();
			sb.append(item2.getId() + "-");
			sb.append(item2.getName() + "-");
			sb.append(item2.getPrice() + "-");
			sb.append(item2.getBuyCount() + "-");
			sb.append(item2.getUnit());
			// д���ı�
			bw.write(sb.toString());
			bw.newLine();
			bw.flush();
		}
		bw.close();
		
	}

	/*
	 * ����һ�����ﳵ����,�����洢��Ҫ�������ƷnewItem
	 */
	private static void addnewItemToShoppingCar(GoodsItem newItem) {

		// �жϹ��ﳵ�������Ƿ��Ѿ�����newItem��ͬid����Ʒ �������ﳵ����
		for (int index = 0; index < shoppingCar.size(); index++) {
			GoodsItem item = shoppingCar.get(index);
			if (newItem.getId().equals(item.getId())) {
				item.setBuyCount(item.getBuyCount() + newItem.getBuyCount());
				return;
			}
		}
		// ���ﳵ�����в�����newItem��ͬid����Ʒ,��ֱ�������Ʒ�����ﳵ����
		shoppingCar.add(newItem);

	}

	/*
	 * �ж������id�Ƿ���ڶ�Ӧ����Ʒ���
	 * */
	private static GoodsItem existIputIdAtList(String inputId) {

		// ��������,�ж��������Ʒid�Ƿ�����ڻ��ܼ�����,�����򷵻ض�Ӧ����Ʒ����
		for (int index = 0; index < list.size(); index++) {
			GoodsItem item = list.get(index);
			if (inputId.equals(item.getId())) {
				return item;
			}
		}
		// ������,�ͷ��ؿ�ֵ
		return null;

	}

	/*
	 * ��ʼ����Ʒ,�����ܼ���
	 */
	private static void addGoodsItemToArrayList() {

		// ������Ʒ����,��ӵ����ܼ�����
		list.add(new GoodsItem("001", "����", 25, "��"));
		list.add(new GoodsItem("002", "ţ��", 35, "��"));
		list.add(new GoodsItem("003", "U��", 88, "��"));
		list.add(new GoodsItem("004", "�Ծ���", 9999, "��"));

	}

}
