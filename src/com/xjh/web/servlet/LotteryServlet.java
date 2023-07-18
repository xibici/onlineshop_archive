package com.xjh.web.servlet;
import com.xjh.domain.Prize;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/lotteryServlet")
public class LotteryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LotteryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml; charset=UTF-8");
        PrintWriter out = response.getWriter();
        String json = getPrize();
        out.write(json);
    }

    private String getPrize() {
        Prize[] pArray = new Prize[7];
        Prize p = null;
        pArray[0] = new Prize(1, new int[] { 1 }, new int[] { 29 }, "法拉利", 1);
        pArray[1] = new Prize(2, new int[] { 302 }, new int[] { 328 }, "iphone12xmas", 2);
        pArray[2] = new Prize(3, new int[] { 242 }, new int[] { 268 }, "iphonese2", 5);
        pArray[3] = new Prize(4, new int[] { 182 }, new int[] { 208 }, "小米mix3", 7);
        pArray[4] = new Prize(5, new int[] { 122 }, new int[] { 148 }, "平衡车",
                10);
        pArray[5] = new Prize(6, new int[] { 62 }, new int[] { 88 }, "2233手办", 25);
        pArray[6] = new Prize(7, new int[] { 32, 92, 152, 212, 272, 332 },
                new int[] { 58, 118, 178, 238, 298, 358 }, "啥都没有", 50);
        int[] arr = new int[8];
        int sum = 0;
        for (Prize prize : pArray) {
            arr[prize.getId()] = prize.getV();
            sum = sum + prize.getV();
        }
        int rid = getRand(arr, sum);
        p = pArray[rid];
        int[] min = p.getMin();
        int[] max = p.getMax();
        Random r = new Random();
        int angle = 0;
        if (p.getId() == 7) {// 七等奖
            int irand = r.nextInt(5);// 0-5随机数
            angle = r.nextInt(max[irand]) % (max[irand] - min[irand] + 1)
                    + min[irand];
        } else {
            angle = r.nextInt(max[0]) % (max[0] - min[0] + 1) + min[0];
        }

        String name = p.getPrize();
        StringBuilder json = new StringBuilder();
        json.append("{\"angle\":").append(angle).append(",\"prize\":\"")
                .append(name).append("\"}");
        return json.toString();

    }

    private int getRand(int[] arr, int sum) {
        int result = 0;
        Random r = new Random();
        int randNum = 0;
        for (int i = 0; i < arr.length; i++) {
            randNum = r.nextInt(sum - 1) + 1;
            if (randNum <= arr[i]) {
                result = i;
                break;
            } else {
                sum -= arr[i];
            }
        }

        return result - 1;
    }


    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
