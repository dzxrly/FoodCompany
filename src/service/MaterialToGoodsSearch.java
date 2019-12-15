package service;

import DAO.HibernateUtils;
import model.MaterialToGoods;
import org.hibernate.Session;

import java.util.List;

public class MaterialToGoodsSearch {//生产财务规划界面

    public List searchMaterialToGoods(String goodsId){//根据商品id 查询原料库中的具体信息 原料库存 原料单价 返回List<MaterialToGoods>
        Session session= HibernateUtils.openSession();
        String hql="";
        List list =null;
        try{
            hql="from MaterialToGoods where goodsId = "+goodsId;
            list=session.createQuery(hql).list();
//            for(int i=0;i<list.size();i++){
//                MaterialToGoods mt= (MaterialToGoods) list.get(i);
//                System.out.println("________________"+mt+"____________________");
//            }
        }catch (RuntimeException e){
            System.out.println("________________Can not____________________");
            throw e;
        }finally {
            session.close();
            return list;
        }

    }
}

//        MaterialToGoodsSearch mt = new MaterialToGoodsSearch();
//        mt.searchMaterialToGoods("2");
