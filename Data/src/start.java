
import service.TbRlzhService2;


public class start {

	public static void main(String[] args) {
		
		//tbrlzh��ӿڶ���
		TbRlzhService2 tbrlzhService = new TbRlzhService2();
		
		//ɾ����
		tbrlzhService.deleteTbRlZh();
		//���÷�����֯
		tbrlzhService.computerNotPerSum();
		tbrlzhService.getAllFrHrOgr();
		//��������֯
		tbrlzhService.computerPerSum();
	}

}
