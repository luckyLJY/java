package com.javapro.stream.Multi_field_matching;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ScanFilter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String user_name = "";
		String status="T";
		String age = "";
		String min_b = "100.76";
		String max_b = "300.76";

		List<BakInfo> bankList = new ArrayList<>();


		bankList.add(new BakInfo("李四","T",  15, 200.76));
		bankList.add(new BakInfo("王五","T",  17, 300.76));
		bankList.add(new BakInfo("赵六","T",  19, 400.76));
		bankList.add(new BakInfo("阿张","F",  15, 200.76));
		bankList.add(new BakInfo("结束","T",  19, 600.76));
		bankList.add(new BakInfo("张三","T",  17, 100.76));

		List<BakInfo> filterEmp = bankList.stream().filter(new Predicate<BakInfo>() {
			@Override
			public boolean test(BakInfo bInfo) {
				boolean ret = false;
				boolean r1 = user_name.length() == 0 ? true : user_name.equals(bInfo.getUserName());
				boolean r2 = status.length() == 0 ? true : status.equals(bInfo.getStatus());
				boolean r3 = age.length() == 0 ? true : bInfo.compareToAge(Integer.parseInt(age)) == 0;
				boolean r4 = min_b.length() == 0 ? true : (bInfo.compareToBalance(new Double(min_b)) >= 0);
				boolean r5 = max_b.length() == 0 ? true : (bInfo.compareToBalance(new Double(max_b)) <= 0);

				if (r1 && r2 && r3 && r4 && r5)
					ret = true;
				return ret;
			}
		}
		).sorted(Comparator.comparing(BakInfo::getAge).thenComparing(BakInfo::getBalance)).collect(Collectors.toList());
		filterEmp.stream().forEach(System.out::println);
	}

}
