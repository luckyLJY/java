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
	
	
	//存储递归结果
	 List<FrHrOgr> tempList1 = new ArrayList<FrHrOgr>();
	
	//删除tb_huan_per表
	public void deleteTbRlZh() {
		TbRlZhDao  TbRlZhDao = new TbRlZhDao();
		TbRlZhDao.delete();
				
	}
	
	//递归查询虚组织及本级组织
	public  void getFrHrOgr(FrHrOgr frhrogr) {
		//存储查询出来的子组织
		List<FrHrOgr> tempList = new ArrayList<FrHrOgr>();
		//创建组织dao
		FrHrOgrDao FrHrOgrdao = new FrHrOgrDao();
		//创建临时组织对象存储本级组织
		FrHrOgr frhrogr1 = new FrHrOgr();
		//查询本级组织信息
		frhrogr1 = FrHrOgrdao.getByFrHrOrgCode1(frhrogr.getCode());
		//查询本级组织的下级组织信息
		tempList = FrHrOgrdao.getByFrHrOrgCode(frhrogr1.getCode());
		//将本级组织加入递归结果集中
		tempList1.add(frhrogr1);
		//判断查询结果若不为0
		if (tempList.size() != 0) {
			for (int i = 0; i < tempList.size(); i++) {
				//进行递归
				getFrHrOgr(tempList.get(i));
			}
		}
	}
	
	
	//循环组织
	public  void computerPerSum() {
		
		FrHrOgrDao FrHrOgrdao = new FrHrOgrDao();
		List<FrHrOgr> frhrogrsList = new ArrayList<FrHrOgr>();
		
		// 存储查询的需组织信息
		frhrogrsList = FrHrOgrdao.getAll2();
		
		for (int j = 0; j < frhrogrsList.size(); j++) {
			//创建tb_rl_zh表的dao对象
			TbRlZhDao TbRlZhdao = new TbRlZhDao();
			
			//创建tb_rl_zh表的对象
			TbRlZh tbrlzh = new TbRlZh();
			
			tbrlzh.setCode(frhrogrsList.get(j).getCode());
			tbrlzh.setFathcode(frhrogrsList.get(j).getFathcode());
			tbrlzh.setGradename(frhrogrsList.get(j).getGradename());
			tbrlzh.setName(frhrogrsList.get(j).getName());
			///创建tb_rl_zh表的dao对象
			
			TbRlZhDao tbrlzhdao = new TbRlZhDao();
			
			//清除存储
			tempList1.clear();
			//调用递归
			 getFrHrOgr(frhrogrsList.get(j));
			 int a=0;
			 for(int i=0;i<tempList1.size();i++) {
				 //存储查询的人员信息
				 List<TbHuanPer> tbhuanpersList = new ArrayList<TbHuanPer>();
				 //创建人员信息查询dao
				 TbHuanPerDao TbHuanPerdao = new TbHuanPerDao();
				 tbhuanpersList = TbHuanPerdao.getByTbhuanperName(tempList1.get(i));
				 a+=tbhuanpersList.size();
			 }
			 tbrlzh.setPersum(a);
			 //System.out.println(tbrlzh.toString());
			 //存储查询对象
			 TbRlZhdao.savePerSum(tbrlzh);
		}

	}

	// 计算单位人员总数
	public  void computerNotPerSum() {
		// 查询fr_hr_ogr表中所有数据
		// 存储查询的非组织信息
		List<FrHrOgr> frhrogrsList = new ArrayList<FrHrOgr>();
		
		// 存储计算出来的信息persum
		List<TbRlZh> tbrlzhsList = new ArrayList<TbRlZh>();
		
		// 查询循环组织
		FrHrOgrDao FrHrOgrdao = new FrHrOgrDao();
		frhrogrsList = FrHrOgrdao.getAll1();

		TbHuanPerDao TbHuanPerdao = new TbHuanPerDao();
		
		// 根据组织名取人员表中查询数据
		for (int i = 0; i < frhrogrsList.size(); i++) {
			// 创建暂存的tb类
			TbRlZh tbrlzhtemp = new TbRlZh();
			
			// 创建通过非循环组织查询出来人员数据
			List<TbHuanPer> tbhuanpersList = new ArrayList<TbHuanPer>();
			
			// 临时组织数据类
			FrHrOgr frhrogrtemp = new FrHrOgr();
			frhrogrtemp = frhrogrsList.get(i);
			
			// System.out.println(frhrogrtemp.getName());
			tbhuanpersList = TbHuanPerdao.getByTbhuanperName(frhrogrtemp);
			
			// System.out.println(frhrogrtemp.getName());
			// System.out.println(tbhuanpersList.size());
			// 判断为非空
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
//		//调用非虚组织
//		computerNotPerSum();
//		//调用虚组织
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

	
//	 * 获取子节点
//	 * 
//	 * @param code    父节点id
//	 * @param allMenu 所有菜单列表
//	 * @return 每个根节点下，所有子菜单列表
//	 
	public static List<FrHrOgr> getChild(String code, List<FrHrOgr> allFrHrOgr) {
		// 子菜单
		List<FrHrOgr> childList = new ArrayList<FrHrOgr>();
		for (FrHrOgr nav : allFrHrOgr) {
			// 遍历所有节点，将所有菜单的父code与传过来的根节点的code比较
			// 相等说明：为该根节点的子节点。
			if (nav.getFathcode().equals(code)) {
				childList.add(nav);
			}
		}
		// 递归
		for (FrHrOgr nav : childList) {
			nav.setChildren(getChild(nav.getCode(), allFrHrOgr));
		}
		// Collections.sort(childList,order());//排序
		// 如果节点下没有子节点，返回一个空List（递归退出）
		if (childList.size() == 0) {
			return new ArrayList<FrHrOgr>();
		}
		return childList;
	}
		
 */
