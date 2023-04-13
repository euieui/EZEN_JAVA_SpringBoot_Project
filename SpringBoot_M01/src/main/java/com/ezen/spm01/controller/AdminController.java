package com.ezen.spm01.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.spm01.dto.Paging;
import com.ezen.spm01.dto.ProductVO;
import com.ezen.spm01.service.AdminService;
import com.ezen.spm01.service.ProductService;
import com.ezen.spm01.service.QnaService;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@Controller
public class AdminController {
	
	@Autowired
	AdminService as;
	
	@Autowired
	ProductService ps;
	
	@Autowired
	ServletContext context;
	
	@Autowired
	QnaService qs;
	
	@RequestMapping("/admin")
	public String admin() {
		return "admin/adminLoginForm";
	}
	
	@RequestMapping("adminLogin")
	public ModelAndView admin_login(Model model, HttpServletRequest request,
			@RequestParam("workId") String workId,
			@RequestParam("workPwd") String workPwd) {
		
		ModelAndView mav = new ModelAndView();
		
		if(workId == null || workId.equals("")) {
			mav.addObject("msg", "아이디를 입력하세요");
			mav.setViewName("admin/adminLoginForm");
			return mav;
		}else if(workPwd == null || workPwd.equals("")) {
			mav.addObject("msg", "패스워드를 입력하세요");
			mav.setViewName("admin/adminLoginForm");
			return mav;
		}
		
		int result = as.workerCheck(workId, workPwd);
		if(result == 1) {
			HttpSession session = request.getSession();
			session.setAttribute("workId", workId);
			mav.setViewName("redirect:/productList");
		}else if(result == 0 ) {
			mav.addObject("message","비밀번호를 확인하세요.");
			mav.setViewName("admin/adminLoginForm");
		}else if(result == -1 ) {
			mav.addObject("message","아이디를 확인하세요.");
			mav.setViewName("admin/adminLoginForm");
		}
		return mav;
		/*
		int result = as.workerCheck(workId,workPwd);
		if(result==1) {
			HttpSession session =request.getSession();
			session.setAttribute("workId", workId);
			mav.setViewName("redirect:/productList");
		}else if(result==0) {
			mav.addObject("message","비밀번호를 확인하세요.");
			mav.setViewName("admin/adminLoginForm");
		}else if(result==-1) {
			mav.addObject("message", "아이디를 확인하세요.");
			mav.setViewName("admin/adminLoginForm");
		}else {
			mav.addObject("message", "이유를 알수 없지만 로그인 안돼요");
			mav.setViewName("admin/adminLoginForm");
		}
		return mav;
		*/
	}
	
	@RequestMapping("productList")
	public ModelAndView product_list(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HttpSession session =request.getSession();
		String id = (String) session.getAttribute("workId");
		
		if(id==null)
			mav.setViewName("redirect:/adminLoginForm");
		else {
			
			if(request.getParameter("first") != null) {
				session.removeAttribute("page");
				session.removeAttribute("key");
			}
			
			int page=1;
			if(request.getParameter("page") != null) {
				page = Integer.parseInt(request.getParameter("page"));
				session.setAttribute("page", page);
			}else if(session.getAttribute("page") != null) {
				page = (Integer) session.getAttribute("page");
			}else {
				page = 1;
				session.removeAttribute("page");
			}
		
		
			String key ="";
			if(request.getParameter("key") != null) {
				key = request.getParameter("key");
				session.setAttribute("key", key);
			}else if (session.getAttribute("key") != null) {
				key = (String)session.getAttribute("key");
			}else {
				session.removeAttribute("key");
				key ="";
			}
			
			Paging paging = new Paging();
			paging.setPage(page);
			
			int count = as.getAllCount("product","name",key);
			paging.setTotalCount(count);
			paging.paging();
			
			mav.addObject("productList",as.listProduct(paging, key));
			mav.addObject("paging",paging);
			mav.addObject("key",key);
			mav.setViewName("admin/product/productList");
		
		}
		return mav;
		
		/*
		if(id==null)
			mav.setViewName("redirect:/admin");
		else {
			int page = 1;
			if(request.getParameter("first") != null && request.getParameter("first").equals("y")){
				page = 1;
				session.removeAttribute("page");
			}else if(request.getParameter("page") != null ) {
				page = Integer.parseInt(request.getParameter("page"));
				session.setAttribute("page", page);
			}else if(session.getAttribute("page") != null) {
				page = (Integer) session.getAttribute("page");
			}else {
				page = 1;
				session.removeAttribute("page");
			}
			
			String key = "";
			if(request.getParameter("first") != null && request.getParameter("first").equals("y")){
				key = null;
				session.removeAttribute("key");
			}else if(request.getParameter("key") != null) {
				key = request.getParameter("key");
				session.setAttribute("key", key);
			} else if (session.getAttribute("key") != null) {
				key = (String) session.getAttribute("key");
			}else {
				session.removeAttribute("key");
				key = "";
			}
			
			
			Paging paging = new Paging();
			paging.setPage(page);
			int count = as.getAllCount("product", "name", key);
			paging.setTotalCount(count);
			paging.paging();
			
			List<ProductVO> productList = as.listProduct(paging, key);
			
			request.setAttribute("paging",paging);
			mav.addObject("productList",productList);
			request.setAttribute("key",key);
			mav.setViewName("admin/product/productList");
		}
		return mav;
		*/
	}
	
	@RequestMapping("productWriteForm")
	public ModelAndView product_write_form(HttpServletRequest request) {
		String kindList[] = {"Heels", "Boots", "Sandals", "Sleepers", "Sneakers" , "On Sale"};
		ModelAndView mav = new ModelAndView();
		mav.addObject("kindList", kindList);
		mav.setViewName("admin/product/productWriteForm");
		return mav;
	}
	
	@RequestMapping(value="/productWrite", method=RequestMethod.POST)
	public String product_write(HttpServletRequest request) {
		String savePath = context.getRealPath("/product_images");
		ProductVO pvo = new ProductVO();
		
		try {
			MultipartRequest multi = new MultipartRequest(
				request, savePath, 5*1024*1024, "UTF-8", new DefaultFileRenamePolicy());
			
			pvo.setKind(multi.getParameter("kind"));
			pvo.setContent(multi.getParameter("content"));
			pvo.setImage(multi.getFilesystemName("image"));
			pvo.setName(multi.getParameter("name"));
			pvo.setPrice1(Integer.parseInt(multi.getParameter("price1")));
			pvo.setPrice2(Integer.parseInt(multi.getParameter("price2")));
			pvo.setPrice3(Integer.parseInt(multi.getParameter("price2")) - Integer.parseInt(multi.getParameter("price1")));
		
		} catch(IOException e ) {e.printStackTrace();
		}
		
		
		as.insertProduct(pvo);
		return "redirect:/productList";
	}
	
	@RequestMapping("adminProductDetail")
	public ModelAndView product_detail(HttpServletRequest request, @RequestParam("pseq") int pseq) {
		ModelAndView mav = new ModelAndView();
		
		ProductVO pvo = ps.getProduct(pseq);
		
		String kindList[] = { "0" , "Heels", "Boots", "Sandals", "Sleepers", "Sneakers" , "On Sale"};
		int index = Integer.parseInt(pvo.getKind());
		
		mav.addObject("productVO",pvo);
		mav.addObject("kind", kindList[index]);
		mav.setViewName("admin/product/productDetail");
		
		return mav;
	}
	
	
	@RequestMapping("productUpdateForm")
	public ModelAndView product_update_form(HttpServletRequest request,
			@RequestParam("pseq") int pseq) {
		ModelAndView mav = new ModelAndView();
		ProductVO pvo = ps.getProduct(pseq);
		mav.addObject("dto",pvo);
		String kindList[] = {"Heels", "Boots", "Sandals", "Sleepers", "Sneakers" , "On Sale"};
		mav.addObject("kindList", kindList);
		mav.setViewName("admin/product/productUpdate");
		return mav;
	}
	
	
	@RequestMapping("/selectimg")
	public String selectimg() {
		return "admin/product/selectimg";
	}
	
	@RequestMapping(value="/fileupload" , method = RequestMethod.POST)
	public String fileupload(Model model , HttpServletRequest request) {
		String path = context.getRealPath("/product_images");
		
		try {
			MultipartRequest multi = new MultipartRequest(
					request, path, 5*1024*1024, "UTF-8", new DefaultFileRenamePolicy()
				);
			model.addAttribute("image",multi.getFilesystemName("image"));
			model.addAttribute("originalFilename",multi.getOriginalFileName("image"));
		} catch(IOException e) {e.printStackTrace();
		}
		return "admin/product/completupload";
	}
	
	@RequestMapping(value="/productUpdate",method= RequestMethod.POST)
	public String productUpdate(@ModelAttribute("dto") @Valid ProductVO productvo,
			@RequestParam("imgfilename") String imgfilename,
			BindingResult result, HttpServletRequest request, Model model) {
		
		if(result.getFieldError("name") != null) {
			model.addAttribute("message", "상품명을 입력하세요.");
			return "admin/product/productUpdate";
		}else if(result.getFieldError("content") != null) {
			model.addAttribute("message", "내용을 입력하세요.");
			return "admin/product/productUpdate";
		}else if(result.getFieldError("price2") != null ) {
			model.addAttribute("message","금액을 입력하세요.");
			return "admin/product/productUpdate";
		}else if(productvo.getImage().equals("")) productvo.setImage(imgfilename);
		as.updateProduct(productvo);
		return "redirect:/productDetail?pseq=" + productvo.getPseq();
	}
}
