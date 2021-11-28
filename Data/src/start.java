
import service.TbRlzhService2;


public class start {

	public static void main(String[] args) {
		
		//tbrlzh表接口对象
		TbRlzhService2 tbrlzhService = new TbRlzhService2();
		
		//删除表
		tbrlzhService.deleteTbRlZh();
		//调用非虚组织
		tbrlzhService.computerNotPerSum();
		tbrlzhService.getAllFrHrOgr();
		//调用虚组织
		tbrlzhService.computerPerSum();
	}

}
