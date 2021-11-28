package service;

import java.util.ArrayList;

import java.util.List;


import dao.FrHrOgrDao;
import dao.TbHuanPerDao;
import dao.TbRlZhDao;
import entity.FrHrOgr;
import entity.TbHuanPer;
import entity.TbRlZh;

public class TbRlzhService {
	
	
	//�洢�ݹ���
	 List<FrHrOgr> tempList1 = new ArrayList<FrHrOgr>();
	
	//ɾ��tb_huan_per��
	public void deleteTbRlZh() {
		TbRlZhDao  TbRlZhDao = new TbRlZhDao();
		TbRlZhDao.delete();
				
	}
	
	//�ݹ��ѯ����֯��������֯
	public  void getFrHrOgr(FrHrOgr frhrogr) {
		//�洢��ѯ����������֯
		List<FrHrOgr> tempList = new ArrayList<FrHrOgr>();
		//������֯dao
		FrHrOgrDao FrHrOgrdao = new FrHrOgrDao();
		//������ʱ��֯����洢������֯
		FrHrOgr frhrogr1 = new FrHrOgr();
		//��ѯ������֯��Ϣ
		frhrogr1 = FrHrOgrdao.getByFrHrOrgCode1(frhrogr.getCode());
		//��ѯ������֯���¼���֯��Ϣ
		tempList = FrHrOgrdao.getByFrHrOrgCode(frhrogr1.getCode());
		//��������֯����ݹ�������
		tempList1.add(frhrogr1);
		//�жϲ�ѯ�������Ϊ0
		if (tempList.size() != 0) {
			for (int i = 0; i < tempList.size(); i++) {
				//���еݹ�
				getFrHrOgr(tempList.get(i));
			}
		}
	}
	
	
	//ѭ����֯
	public  void computerPerSum() {
		
		FrHrOgrDao FrHrOgrdao = new FrHrOgrDao();
		List<FrHrOgr> frhrogrsList = new ArrayList<FrHrOgr>();
		
		// �洢��ѯ������֯��Ϣ
		frhrogrsList = FrHrOgrdao.getAll2();
		
		for (int j = 0; j < frhrogrsList.size(); j++) {
			//����tb_rl_zh���dao����
			TbRlZhDao TbRlZhdao = new TbRlZhDao();
			
			//����tb_rl_zh��Ķ���
			TbRlZh tbrlzh = new TbRlZh();
			
			tbrlzh.setCode(frhrogrsList.get(j).getCode());
			tbrlzh.setFathcode(frhrogrsList.get(j).getFathcode());
			tbrlzh.setGradename(frhrogrsList.get(j).getGradename());
			tbrlzh.setName(frhrogrsList.get(j).getName());
			///����tb_rl_zh���dao����
			
			TbRlZhDao tbrlzhdao = new TbRlZhDao();
			
			//����洢
			tempList1.clear();
			//���õݹ�
			 getFrHrOgr(frhrogrsList.get(j));
			 int a=0;
			 for(int i=0;i<tempList1.size();i++) {
				 //�洢��ѯ����Ա��Ϣ
				 List<TbHuanPer> tbhuanpersList = new ArrayList<TbHuanPer>();
				 //������Ա��Ϣ��ѯdao
				 TbHuanPerDao TbHuanPerdao = new TbHuanPerDao();
				 tbhuanpersList = TbHuanPerdao.getByTbhuanperName(tempList1.get(i));
				 a+=tbhuanpersList.size();
			 }
			 tbrlzh.setPersum(a);
			 //System.out.println(tbrlzh.toString());
			 //�洢��ѯ����
			 TbRlZhdao.savePerSum(tbrlzh);
		}

	}

	// ���㵥λ��Ա����
	public  void computerNotPerSum() {
		// ��ѯfr_hr_ogr������������
		// �洢��ѯ�ķ���֯��Ϣ
		List<FrHrOgr> frhrogrsList = new ArrayList<FrHrOgr>();
		
		// �洢�����������Ϣpersum
		List<TbRlZh> tbrlzhsList = new ArrayList<TbRlZh>();
		
		// ��ѯѭ����֯
		FrHrOgrDao FrHrOgrdao = new FrHrOgrDao();
		frhrogrsList = FrHrOgrdao.getAll1();

		TbHuanPerDao TbHuanPerdao = new TbHuanPerDao();
		
		// ������֯��ȡ��Ա���в�ѯ����
		for (int i = 0; i < frhrogrsList.size(); i++) {
			// �����ݴ��tb��
			TbRlZh tbrlzhtemp = new TbRlZh();
			
			// ����ͨ����ѭ����֯��ѯ������Ա����
			List<TbHuanPer> tbhuanpersList = new ArrayList<TbHuanPer>();
			
			// ��ʱ��֯������
			FrHrOgr frhrogrtemp = new FrHrOgr();
			frhrogrtemp = frhrogrsList.get(i);
			
			// System.out.println(frhrogrtemp.getName());
			tbhuanpersList = TbHuanPerdao.getByTbhuanperName(frhrogrtemp);
			
			// System.out.println(frhrogrtemp.getName());
			// System.out.println(tbhuanpersList.size());
			// �ж�Ϊ�ǿ�
			if (!tbhuanpersList.isEmpty()) {
				tbrlzhtemp.setCode(frhrogrtemp.getCode());
				tbrlzhtemp.setName(frhrogrtemp.getName());
				tbrlzhtemp.setFathcode(frhrogrtemp.getFathcode());
				tbrlzhtemp.setGradename(frhrogrtemp.getGradename());
				tbrlzhtemp.setPersum(tbhuanpersList.size());
				TbRlZhDao tbrlzhdao = new TbRlZhDao();
				int j = tbrlzhdao.savePerSum(tbrlzhtemp);
				// System.out.println(j);
			}

		}
	}

//	public static void main(String[] args) {
//		/*
//		//���÷�����֯
//		computerNotPerSum();
//		//��������֯
//		computerPerSum();*/
//	}
}
/*
 // computerPerSum();

//		List<FrHrOgr> frhrogrsList2 = new ArrayList<FrHrOgr>();
//		FrHrOgrDao FrHrOgrdao = new FrHrOgrDao();
//		//frhrogrsList = FrHrOgrdao.getAll2();
//		
//		frhrogrsList2= FrHrOgrDao.getAll();
//		
//		List<FrHrOgr> frhrogrsList =treeMenuList(frhrogrsList2,"11");
//		
//		//frhrogrsList=getChild("11",frhrogrsList2);
//		System.out.println(frhrogrsList.size());
//		for(int i=0;i<frhrogrsList.size();i++) {
//			System.out.println(frhrogrsList.get(i).getCode());
//			System.out.println(frhrogrsList.get(i).getName());
//		}
	
		FrHrOgr frhrogr = new FrHrOgr();
		frhrogr.setCode("100");
	
		List<FrHrOgr> result = new ArrayList<FrHrOgr>();
		
		List<FrHrOgr> result1 = new ArrayList<FrHrOgr>();
		  getFrHrOgr(frhrogr);
		  
		  for(int i=0;i<tempList1.size();i++) {
		  
		  System.out.println(tempList1.get(i).getCode());
		  System.out.println(tempList1.get(i).getName()); 
		  } 
		  tempList1.clear();
			FrHrOgr frhrogr1 = new FrHrOgr();
			frhrogr1.setCode("101");
		   getFrHrOgr(frhrogr1); 
		 // System.out.println(result1.size());
		  for(int i=0;i<tempList1.size();i++) {
		  
		  System.out.println(tempList1.get(i).getCode());
		  System.out.println(tempList1.get(i).getName()); 
		  }
		 
	}
		
//		FrHrOgr frhrogr = new FrHrOgr();
//		frhrogr.setCode("100");
//		 getFrHrOgr(frhrogr);
//		for(int i=0;i<tempList1.size();i++) {
//			  
//			  System.out.println(tempList1.get(i).getCode());
//			 
//			  }
//			 
 * public Comparator<FrHrOgr> order() {
		Comparator<FrHrOgr> comparator = new Comparator<FrHrOgr>() {
			@Override
			public int compare(FrHrOgr o1, FrHrOgr o2) {
				if (o1.getOrder() != o2.getOrder()) {
					return o1.getOrder() - o2.getOrder();
				}
				return 0;
			}
		};
		return comparator;
	}

	
//	 * ��ȡ�ӽڵ�
//	 * 
//	 * @param code    ���ڵ�id
//	 * @param allMenu ���в˵��б�
//	 * @return ÿ�����ڵ��£������Ӳ˵��б�
//	 
	public static List<FrHrOgr> getChild(String code, List<FrHrOgr> allFrHrOgr) {
		// �Ӳ˵�
		List<FrHrOgr> childList = new ArrayList<FrHrOgr>();
		for (FrHrOgr nav : allFrHrOgr) {
			// �������нڵ㣬�����в˵��ĸ�code�봫�����ĸ��ڵ��code�Ƚ�
			// ���˵����Ϊ�ø��ڵ���ӽڵ㡣
			if (nav.getFathcode().equals(code)) {
				childList.add(nav);
			}
		}
		// �ݹ�
		for (FrHrOgr nav : childList) {
			nav.setChildren(getChild(nav.getCode(), allFrHrOgr));
		}
		// Collections.sort(childList,order());//����
		// ����ڵ���û���ӽڵ㣬����һ����List���ݹ��˳���
		if (childList.size() == 0) {
			return new ArrayList<FrHrOgr>();
		}
		return childList;
	}
		
 */
