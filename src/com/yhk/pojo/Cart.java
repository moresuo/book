package com.yhk.pojo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
   /* private Integer totalCount;
    private BigDecimal totalPrice;*/
    private Map<Integer, CartItem> items = new HashMap<Integer, CartItem>();

    public Cart() {
    }

    public Cart(Map<Integer, CartItem> items) {
        this.items = items;

    }

    public Integer getTotalCount() {
        Integer totalCount=0;
        for(Map.Entry<Integer,CartItem>entry:items.entrySet()){
            totalCount+=entry.getValue().getCount();
        }
        return totalCount;
    }

    //不能根据前端提交的数据设置总数，应该是根据商品项之和
    /*public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }*/

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        for(Map.Entry<Integer,CartItem>entry:items.entrySet()){
            totalPrice = totalPrice.add(entry.getValue().getTotalPrice());
        }
        return totalPrice;
    }
    //不能根据前端传递的数据设置总金额，应该根据每件商品的总金额累加而成
    /*public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }*/

    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }


    /**
     * 添加商品项
     * @param cartItem
     */
    public void addItem(CartItem cartItem){
        //通过获取所添加的商品项的id,来判断items集合中是否有相同的商品项
        CartItem item=items.get(cartItem.getId());
        if(item==null){
            items.put(cartItem.getId(),cartItem);
        }else{
            //已经添加过了
            //更新某个商品的数量
            item.setCount(item.getCount() + 1);
            //更新某个商品的总金额
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));
        }
    }

    /**
     * 根据id删除商品项
     *
     * @param id
     */
    public void deleteItem(Integer id) {
        //根据id删除
        items.remove(id);

    }

    /**
     * 清空购物车
     */
    public void clearCart(){
        items.clear();
    }

    /**
     * 修改商品数量
     * @param id 所修改商品项id
     * @param count 要修改的数量
     */
    public void updateCount(Integer id,Integer count){
        //先检查购物车中是否有该商品
        CartItem item=items.get(id);
        if(item!=null){
            //修改该商品数量
            item.setCount(count);
            //修改总金额
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));

        }
    }

    @Override
    public String toString() {
        return "Cart{" +
                "totalCount=" + getTotalCount() +
                ", totalPrice=" + getTotalPrice() +
                ", items=" + items +
                '}';
    }
}
