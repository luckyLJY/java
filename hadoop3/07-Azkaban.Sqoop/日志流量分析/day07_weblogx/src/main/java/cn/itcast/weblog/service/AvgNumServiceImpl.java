package cn.itcast.weblog.service;

import cn.itcast.weblog.mapper.AvgNumMapper;
import cn.itcast.weblog.pojo.AvgPvNumPojo;
import cn.itcast.weblog.pojo.AvgReturnPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AvgNumServiceImpl implements AvgNumService{
    @Autowired
    private AvgNumMapper avgNumMapper;
    @Override
    public AvgReturnPojo getAvgReturnJson() {
        AvgReturnPojo avgReturnPojo = new AvgReturnPojo();
        ArrayList<String> dates = new ArrayList<>();
        ArrayList<String> data = new ArrayList<>();
        
        List<AvgPvNumPojo>  avgPvNumPojoList = avgNumMapper.getAllAvgNum();
        for (AvgPvNumPojo avgPvNumPojo : avgPvNumPojoList) {
            dates.add(avgPvNumPojo.getDateStr());
            data.add(avgPvNumPojo.getAvgPvNum());
        }
        avgReturnPojo.setData(data);
        avgReturnPojo.setDates(dates );
        return avgReturnPojo;
    }
}
