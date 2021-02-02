package com.wwe.storage.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wwe.common.util.file.FileUtils;
import com.wwe.storage.model.service.StorageService;


@WebServlet("/storage/*")
public class StorageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	StorageService storageService = new StorageService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StorageController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] uriArr = request.getRequestURI().split("/");
		switch (uriArr[uriArr.length-1]) {
		case "personal":
			personalStorage(request,response);
			break;
		case "share":
			shareStorage(request,response);
			break;
		case "upload":
			uploadFile(request,response);
			break;
		case "download":
			downloadFile(request,response);
			break;	
		case "delete":
			deleteFile(request,response);
			break;	
		default:
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void personalStorage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idx = "sunmin";
		Map<String, Object> commandMap = storageService.selectStorage(idx);
		request.setAttribute("data", commandMap);
		request.getRequestDispatcher("/WEB-INF/view/storage/personal_storage.jsp").forward(request, response);
	}
	
	private void shareStorage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/storage/share_storage.jsp").forward(request, response);
	}
	
	private void uploadFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 파일 업로드
		HttpSession session = request.getSession();
		// 유저 객체 생성할곳
		request.setAttribute("filterPath", "sunmin");
		storageService.insertStroage("sunmin", request);
		response.sendRedirect("/storage/personal");
		//request.getRequestDispatcher("/WEB-INF/view/storage/personal_storage.jsp").forward(request, response);
	}
	
	private void downloadFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String originName = request.getParameter("ofname");
		String reName = request.getParameter("rfname");
		String path = request.getParameter("savePath");
		
		response.setHeader("content-disposition", "attachment; filename="+originName);
		request.getRequestDispatcher("/file"+path+reName).forward(request, response);

		//request.getRequestDispatcher("/WEB-INF/view/storage/personal_storage.jsp").forward(request, response);
	}
	
	private void deleteFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String originName = request.getParameter("ofname");
		String reName = request.getParameter("rfname");
		String path = request.getParameter("savePath");
		
		new FileUtils().deleteFile(path+reName);
		response.sendRedirect("/storage/personal");
		
		//response.setHeader("content-disposition", "attachment; filename="+originName);
		//request.getRequestDispatcher("/file"+path+reName).forward(request, response);

		//request.getRequestDispatcher("/WEB-INF/view/storage/personal_storage.jsp").forward(request, response);
	}

}















