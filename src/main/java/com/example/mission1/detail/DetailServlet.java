package com.example.mission1.detail;

import com.example.mission1.wifi.Wifi;
import com.example.mission1.wifi.WifiRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/DetailServlet")
public class DetailServlet extends HttpServlet {

    private final WifiRepository wifiRepository = new WifiRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String manageNo = request.getParameter("manageNo");

        Wifi wifiInfo = wifiRepository.getWifiInfoByManageNo(manageNo);

        request.setAttribute("wifiInfo", wifiInfo);

        RequestDispatcher dispatcher = request.getRequestDispatcher("detail.jsp");
        dispatcher.forward(request, response);
    }
}





