package com.controller;

import com.config.ExceptionConfig;
import com.entity.*;
import com.service.CartsService;
import com.service.GoodsService;
import com.service.OrderService;
import com.service.TypesService;
import com.utils.PageHtmlUtils;
import com.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author linxiaobai
 * @date 2020
 */
@Controller
@RequestMapping("/index")
public class IndexController {
    @Autowired
    GoodsService goodsService;
    @Autowired
    TypesService typesService;
    @Autowired
    CartsService cartsService;
    @Autowired
    OrderService orderService;

    /**
     * 商城首页
     */
    @GetMapping("/index")
    public String index(HttpServletRequest request) {
        request.setAttribute("flag", 1);
        //加载今日推荐
        List<Goods> todayList = goodsService.getTypesToTopsGoods(Tops.TYPE_TODAY, 1, 6);
        request.setAttribute("todayList", todayList);
        //加载热销排行
        List<Goods> hotList = goodsService.getAllHot(1, 10);
        request.setAttribute("hotList", hotList);
        //通过类型把所有商品加载
        List<Types> typesList = typesService.getAllTypes();
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (Types types :
                typesList) {
            Map<String, Object> map = new HashMap<>();
            map.put("type", types);
            map.put("goodList", goodsService.getTypesGoods(types.getId(), 1, 15));
            dataList.add(map);
        }
        request.setAttribute("dataList", dataList);
        return "/index/index.jsp";
    }

    /**
     * 产品分类
     */
    @GetMapping("/type")
    public String type(HttpServletRequest request,
                       @RequestParam(required = false, defaultValue = "0") int id,
                       @RequestParam(required = false, defaultValue = "1") int page,
                       @RequestParam(required = false, defaultValue = "10") int size
    ) {
        Types types = typesService.getByIdTypes(id);
        List<Goods> list = goodsService.getTypesGoods(id, page, size);
        request.setAttribute("type", types);
        request.setAttribute("goodList", list);
        request.setAttribute("pageHtml", PageHtmlUtils.getPageHtml(request, goodsService.getTypeIdToGoodsCount(id), page, size));
        return "/index/goods.jsp";
    }

    /**
     * 今日推荐
     */
    @GetMapping("/today")
    public String today(HttpServletRequest request, HttpSession session,
                        @RequestParam(required = false, defaultValue = "1") int page,
                        @RequestParam(required = false, defaultValue = "10") int size
    ) {
        List<Goods> list = goodsService.getTypesToTopsGoods(Tops.TYPE_TODAY, page, size);
        request.setAttribute("flag", 2);
        request.setAttribute("goodList", list);
        request.setAttribute("pageHtml", PageHtmlUtils.getPageHtml(request, goodsService.getCountTops(Tops.TYPE_TODAY), page, size));
        if (session.getAttribute("user") != null && Objects.nonNull(session.getAttribute("user"))) {
            User user = (User) session.getAttribute("user");
            session.setAttribute("cartCount", cartsService.getCartsCount(user.getId()));
        }
        return "/index/goods.jsp";
    }

    /**
     * 热销排行
     */
    @GetMapping("/hot")
    public String hot(HttpServletRequest request, HttpSession session,
                      @RequestParam(required = false, defaultValue = "1") int page,
                      @RequestParam(required = false, defaultValue = "10") int size
    ) {
        List<Goods> list = goodsService.getAllHot(page, size);
        request.setAttribute("flag", 3);
        request.setAttribute("goodList", list);
        request.setAttribute("pageHtml", PageHtmlUtils.getPageHtml(request, goodsService.getAllHotCount(), page, size));
        if (session.getAttribute("user") != null && Objects.nonNull(session.getAttribute("user"))) {
            User user = (User) session.getAttribute("user");
            session.setAttribute("cartCount", cartsService.getCartsCount(user.getId()));
        }
        return "/index/goods.jsp";
    }

    /**
     * 新品上市
     */
    @GetMapping("/new")
    public String newGoods(HttpServletRequest request, HttpSession session,
                           @RequestParam(required = false, defaultValue = "1") int page,
                           @RequestParam(required = false, defaultValue = "10") int size
    ) {
        List<Goods> list = goodsService.getList(page, size);
        request.setAttribute("flag", 4);
        request.setAttribute("goodList", list);
        request.setAttribute("pageHtml", PageHtmlUtils.getPageHtml(request, goodsService.getListCount(), page, size));
        if (session.getAttribute("user") != null && Objects.nonNull(session.getAttribute("user"))) {
            User user = (User) session.getAttribute("user");
            session.setAttribute("cartCount", cartsService.getCartsCount(user.getId()));
        }
        return "/index/goods.jsp";
    }

    /**
     * 点击商品显示详情
     */
    @GetMapping("/detail")
    public String detail(int id, HttpServletRequest request) {
//        System.out.println(id);
        Goods good = goodsService.getGoodsById(id);
        request.setAttribute("good", good);
        request.setAttribute("todayList", goodsService.getTypesToTopsGoods(Tops.TYPE_TODAY, 1, 2));
        return "/index/detail.jsp";
    }

    /**
     * 购物车详情
     */
    @GetMapping("/cart")
    public String cart(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        List<Carts> cartList = cartsService.getAllCarts(user.getId());
        request.setAttribute("cartList", cartList);
        //购物车右上角的数字
        HttpSession session = request.getSession();
        session.setAttribute("cartCount", cartsService.getCartsCount(user.getId()));
//        System.out.println("购物车："+cartsService.getCartsCount(user.getId()));
        double cartTotal = 0;
        for (int i = 0; i < cartList.size(); i++) {
            cartTotal += cartList.get(i).getAmount() * cartList.get(i).getGood().getPrice();
        }
        request.setAttribute("cartTotal", cartTotal);
        return "/index/cart.jsp";
    }

    /**
     * 将商品添加到购物车
     */
    @PostMapping("/cartBuy")
    public @ResponseBody
    boolean addCart(HttpSession session, int goodId) {
        User user = (User) session.getAttribute("user");
        boolean type = cartsService.saveGoodsToCarts(user.getId(), goodId);
        //更新购物车商品数量
//        session.setAttribute("cartCount", cartsService.getCartsCount(user.getId()));
        return type;
    }

    /**
     * 购物车中点击添加商品
     */
    @PostMapping("/cartAdd")
    public @ResponseBody
    boolean cartAdd(HttpSession session, @RequestParam("id") int id) {
        boolean type = cartsService.AddCarts(id);
        //更新购物车商品数量（右上角）
//        User user = (User) session.getAttribute("user");
//        session.setAttribute("cartCount", cartsService.getCartsCount(user.getId()));
        return type;
    }

    /**
     * 添加减少按钮都要用的计算总价
     * @param request
     * @return
     */
    @GetMapping("/cartTotal")
    public @ResponseBody
    double cartTotal(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        List<Carts> cartList = cartsService.getAllCarts(user.getId());
        double cartTotal = 0;
        for (int i = 0; i < cartList.size(); i++) {
            cartTotal += cartList.get(i).getAmount() * cartList.get(i).getGood().getPrice();
        }
        request.setAttribute("cartTotal", cartTotal);
        return cartTotal;
    }

    /**
     * 购物车中点击减少按钮
     *
     * @param id
     * @return
     */
    @PostMapping("/cartLess")
    public @ResponseBody
    int cartLess(@RequestParam("id") int id) {
        //如果数量小于等于1，点击减号就删除  否则减一
        int num = cartsService.getCartsById(id).getAmount();
        if (num <= 1) {
            boolean type = cartsService.cartDelete(id);
            if (true == type) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return cartsService.cartLess(id);
        }
    }

    /**
     * 点击删除商品图标
     */
    @PostMapping("/cartDelete")
    public @ResponseBody
    boolean cartDelete(@RequestParam("id") int id) {
        return cartsService.cartDelete(id);
    }

    /**
     * @return
     */
    @GetMapping("/order")
    public String order(HttpServletRequest request,HttpSession session,
                        @RequestParam(required = false,defaultValue = "1")int page,
                        @RequestParam(required = false,defaultValue = "6")int size)
    {
        User users= (User) session.getAttribute("user");
        request.setAttribute("orderList", orderService.getMyOrder(users.getId(), page, size));
        request.setAttribute("pageHtml", PageUtil.getPageHtml(request, orderService.getCountOrder(users.getId()), page, size));
        return "/index/order.jsp";
    }

    /**
     * 支付界面（将购物车的内容添加到订单表（orders））
     */
    @GetMapping("/orderSave")
    public String pay(HttpSession session) throws ExceptionConfig.MyException {
        User user = (User) session.getAttribute("user");
        int orderId = orderService.CartsToOrder(user.getId());
        //清空购物车
        session.removeAttribute("cartCount");
        return "redirect:orderPay?id=" + orderId;//跳转支付页面
    }

    /**
     * 支付页面
     */
    @GetMapping("/orderPay")
    public String order(int id, HttpServletRequest request) {
        request.setAttribute("order", orderService.getOrder(id));
        return "/index/pay.jsp";
    }

    /**
     * 付款成功界面
     */
    @PostMapping("/orderPay")
    public String orderPay(Order order) {
        orderService.pay(order);
        return "/index/payok.jsp";
    }
}
