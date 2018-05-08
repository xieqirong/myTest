package com.itheima01;

import java.util.ArrayList;
import java.util.Scanner;

public class ShoppingReciept {
	//�������ϴ��������Ʒ��
	static ArrayList<GoodsItem> items = new ArrayList<>();
	//�����ӹ��ﳵ����
	static ArrayList<GoodsItem> shoppingCar = new ArrayList<>();
	//�����I�P���ϵ�y
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		
		//��ʼ����Ʒ��Ϣ(���÷���)
		initGoodsItem();
		//��ʾ������
		System.out.println("\t��ӭʹ�ó��й���ϵͳ");

		//ѭ�hʹ��ُ��ϵ�y
		while(true){
		System.out.println("��������Ҫ���еĲ���");
		System.out.println("1.������Ʒ          2.���㲢��ӡСƱ       3.�˳�ϵͳ");
		//�����û��Ĳ�������
		int optNumber = Integer.parseInt(sc.nextLine());;
		//ʹ��switchƥ���û����������
		switch (optNumber) {
		case 1:
			//"������Ʒ";
			buyGoodsItem();
			break;
		case 2:
			//"���㲢��ӡСƱ";
			printTicket();
			break;
		case 3:
			//�˳�����ϵͳ
			System.out.println("��лʹ�ù���ϵͳ,��ӭ�´ι���");
			System.exit(0);
			break;

		default:
			System.out.println("������Ĳ���������");
			break;
		}
				
		}
	}
	
	/*
	 * ���㲢��ӡСƱ
	 * 
	 * */
	private static void printTicket() {
		/*
		 * �жϹ��ﳵ���Ƿ�����Ʒ,�оͱ������ﳵ����,ͳ�Ƽ۸�����,�����ӡ
		 * û�о���ʾ�û��ȹ�����ٽ���
		 * */
		if (shoppingCar.size()==0) {
			//������ʱ
			System.out.println("�㻹û�й�����Ʒ,���ȹ�����Ʒ�ٽ���");
			return;
		}
		//���ﳵ������Ʒ��Ҫ����ʱ
		//���Ʊͷ
		System.out.println("....................");
		System.out.println("\t��ӭ����");
		System.out.println("����\t�ۼ�\t����\t���");
		System.out.println("....................");
		
		//���������¼�����˶��ټ���Ʒ
		int totalCount = 0;
		//���������¼������Ʒ���ܽ��
		double totalMoney = 0;
		//���Ʊ��
		//�������ﳵ����
		for (int index = 0; index < shoppingCar.size(); index++) {
			GoodsItem item = shoppingCar.get(index);
			//�����Ӧ��Ʒ�Ľ��
			double totalPrice = item.getPrice()*item.getBuyCount();
			//�ۼ���Ʒ����
			totalCount += item.getBuyCount();
			//�ۼ���Ʒ���,�õ��ܽ��
			totalMoney += totalPrice;
			System.out.println(item.getName()+"\t"+item.getPrice()+
					"\t"+item.getBuyCount()+"\t"+totalPrice);
		}
		System.out.println(".......................");
		
		//���Ʊβ
		System.out.println(shoppingCar.size()+"����Ʒ");
		System.out.println("����"+totalCount+"��");
		System.out.println("��"+totalMoney+"Ԫ");
		System.out.println(".......................");
		
		//�����,��չ��ﳵ
		shoppingCar.clear();
		//shoppingCar=null;//(���ܰѹ��ﳵ����)
	}
	/**
	 * ������Ʒ
	 * */
	private static void buyGoodsItem() {
		//չʾ���е���Ʒ
		System.out.println(".......................");
		System.out.println("\t��Ʒ�б�:");
		System.out.println("��Ʒid\t����\t����\t�Ƽ۵�λ");
		//��������
		for (int index = 0; index < items.size(); index++) {
			GoodsItem item = items.get(index);
			//�����Ʒ��Ϣ
			System.out.println(item.getId()+"\t"+item.getName()+"\t"
			+item.getPrice()+"\t"+item.getUnit());
		}
		System.out.println(".......................");
		//��ʾ�ͻ����빺����Ϣ
		System.out.println("��������Ҫ�������Ʒ��(��ʽ:id-��������),����endb��ʾ�������");
		//ʹ��ѭ������
		while (true) {
			//�����û�����Ĺ�����Ϣ
			String input = sc.nextLine();
			//�ж��Ƿ��ǽ�������
			if(input.equals("end")){
				System.out.println("��������");
				break;
			}
			//ʹ��  - �и���Ϣ�ַ���
			String[] strs = input.split("-");
			//�ж��û�����ĸ�ʽ�Ƿ���ȷ
			if (strs.length!=2) {
				System.out.println("����������Ʋ���,�뻻����������...");
				continue;
			}
			//�����Ʒid
			String id = strs[0];//strs[1];??
			//�ж��û������id�Ƿ����
			GoodsItem item = findGoodsItemById(id);
			
			if(item==null){
				//��Ʒ������
				System.out.println("�㹺�����Ʒ������,����������");
				continue;
			}
			
			//id��Ӧ����Ʒ����,�򴴽��µ���Ʒ��:�µ���Ʒ�����ԺͶ�Ӧ�û�����(items������)��item�����������ȫ��ͬ,ͬʱ���Ϲ��������������
			GoodsItem newItem = new GoodsItem(item.getId(),item.getName(),item.getPrice(),
					item.getUnit(),Integer.parseInt(strs[1]));
			//(����ʱ),Ȼ����Ʒ����ڹ��ﳵ������,���÷���
			addGoodsItemToShoppingCar(newItem);
			
		}
		

	}
	
	/*
	 * �������ﳵ ������Ʒ��
	 * */
	
	private static void addGoodsItemToShoppingCar(GoodsItem newItem) {
		//�ж���Ʒ�Ƿ�����ڹ��ﳵ��,�ȱ������ﳵ����
		for (int index = 0; index < shoppingCar.size(); index++) {
			GoodsItem item = shoppingCar.get(index);
			//�ж��Ƿ����
			if(item.getId().equals(newItem.getId())){
				//������   ���ڹ��ﳵ��,ֻ��Ҫ�ı�ԭ���Ĺ�������  �ö����set������������
				item.setBuyCount(item.getBuyCount()+newItem.getBuyCount());
				return;
			}
		}
		
		//ʱ�µ�Ҫ���ӵ���Ʒ,����Ʒ�����ӵ����ﳵ��
		shoppingCar.add(newItem);
		
	}
	/*
	 * ����id������Ʒ��
	 * */
	private static GoodsItem findGoodsItemById(String id) {
		//������Ʒ����
		for (int index = 0; index <items.size(); index++) {
			//���������Ӧ����Ʒ��
			GoodsItem item = items.get(index);
			//�ж���Ʒ���id�������id�Ƿ���ͬ
			if (item.getId().equals(id)) {
				//���ڷ��ض�Ӧ����
				return item;
			}
		}
	
		//�����ڷ��� ��
		return null;
	}
	/**
	 * ��ʼ����Ʒ��Ϣ
	 */

	private static void initGoodsItem() {
	
		//������Ʒ�����ӵ�������
		items.add(new GoodsItem("001","���ֺ���",15.5,"��"));
		items.add(new GoodsItem("002","�п�����",14.5,"��"));
		items.add(new GoodsItem("003","�ƶ�Ӳ��",345.0,"��"));
		items.add(new GoodsItem("004","��������",199.0,"G"));
		
	}

}