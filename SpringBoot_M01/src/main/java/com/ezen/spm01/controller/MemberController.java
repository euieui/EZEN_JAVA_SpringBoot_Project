package com.ezen.spm01.controller;

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

import com.ezen.spm01.dto.MemberVO;
import com.ezen.spm01.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	MemberService ms;
	
	@RequestMapping("loginForm")
	public String login_form() {
		return "member/login";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@ModelAttribute("dto") @Valid MemberVO membervo,
			BindingResult result, Model model, HttpServletRequest request) {
		
		if(result.getFieldError("id") != null ) {
			model.addAttribute("message", result.getFieldError("id").getDefaultMessage());
			return "member/login";
		}else if(result.getFieldError("pwd") != null ) {
			model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());
			return "member/login";
		}
		
		MemberVO mvo = ms.getMember(membervo.getId());
		if(mvo==null) {
			model.addAttribute("message","ID가 없습니다.");
			return "member/login";
		} else if(mvo.getPwd() == null ) {
			model.addAttribute("message","관리자에게 문의하세요");
			return "member/login";
		} else if(!mvo.getPwd().equals(membervo.getPwd())) {
			model.addAttribute("message","비번이 맞지 않습니다.");
			return "member/login";
		} else if(mvo.getPwd().equals(membervo.getPwd())) {
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", mvo);
			return "redirect:/";
		} else {
			model.addAttribute("message","d알수 없는 이유로 로그인이 안됩니다.");
			return "member/login";
		}
	}
	
	@RequestMapping("logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("loginUser");
		return "redirect:/";
	}
	
	@RequestMapping("contract")
	public String contract() {
		return "member/contract";
	}
	
	@RequestMapping(value="joinForm",method=RequestMethod.POST)
	public String join_form() {
		return "member/joinForm";
	}
	
	@RequestMapping("idCheckForm")
	public ModelAndView id_check_form(@RequestParam("id") String id) {
		ModelAndView mav = new ModelAndView();
		
		MemberVO mvo = ms.getMember(id);
		int result = 0;
		if(mvo == null) result= -1;
		else result = 1;
		mav.addObject("result",result);
		mav.addObject("id",id);
		mav.setViewName("member/idcheck");
		return mav;
	}
	
	@RequestMapping("findZipNum")
	public ModelAndView find_zip(@RequestParam(value="dong", required=false) String dong) {
		// RequestParam null 값일때 에러가 뜨는데 required=false를 주어서 그냥 넘어간다!?
		ModelAndView mav= new ModelAndView();
		if(dong != null && dong.trim().equals("")==false) {
			mav.addObject("addressList",ms.selectAddressByDong(dong));
		}
		mav.setViewName("member/findZipNum");
		return mav;
	}
	
	@RequestMapping(value="join", method=RequestMethod.POST)
	public String join(@ModelAttribute("dto") @Valid MemberVO membervo,
			BindingResult result, Model model,HttpServletRequest request,
			@RequestParam(value="reid", required=false) String reid,
			@RequestParam(value="pwdCheck", required=false) String pwdCheck) {
		
		if(result.getFieldError("id") != null) {
			model.addAttribute("message",result.getFieldError("id").getDefaultMessage());
			return "member/joinForm";
		}else if(result.getFieldError("pwd") != null) {
			model.addAttribute("message",result.getFieldError("pwd").getDefaultMessage());
			return "member/joinForm";
		}else if(result.getFieldError("name") != null) {
			model.addAttribute("message",result.getFieldError("name").getDefaultMessage());
			return "member/joinForm";
		}else if(result.getFieldError("email") != null) {
			model.addAttribute("message",result.getFieldError("email").getDefaultMessage());
			return "member/joinForm";
		}else if(reid == null || (reid != null && !reid.equals(membervo.getId()))) {
			model.addAttribute("message","아이디 중복체크를 하지 않으셨습니다.");
			return "member/joinForm";
		}else if(pwdCheck == null || (pwdCheck != null && !pwdCheck.equals(membervo.getPwd()))) {
			model.addAttribute("message","비밀번호 확인 일치하지 않습니다.");
			return "member/joinForm";
		}
		membervo.setAddress(request.getParameter("addr1") + " " +request.getParameter("addr2"));
		ms.insertMember(membervo);
		model.addAttribute("message", "회원가입이 완료되었어요. 로그인하세요");
		return "member/login";
	}
	
	
	@RequestMapping(value = "memberEditForm")
	public ModelAndView member_Edit_Form(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("loginUser");
		
		String addr = mvo.getAddress(); // 주소 추출
		int k1 = addr.indexOf(" ");
		int k2 = addr.indexOf(" ", k1 +1);
		int k3 = addr.indexOf(" ", k2 + 1);
		
		String addr1 = addr.substring(0, k3);
		String addr2 = addr.substring(k3+1);
		
		
		mav.addObject("dto",mvo);
		mav.addObject("addr1",addr1);
		mav.addObject("addr2",addr2);
		mav.setViewName("member/memberUpdateForm");
		return mav;
		
	}
	
	@RequestMapping(value="memberUpdate", method=RequestMethod.POST)
	public String memberUpdate(@ModelAttribute("dto") @Valid MemberVO membervo,
			BindingResult result, Model model, HttpServletRequest request) {
		request.setAttribute("addr1", request.getParameter("addr1"));
		request.setAttribute("addr2", request.getParameter("addr2"));
		String pwdCheck = request.getParameter("pwdCheck");
		
		if(result.getFieldError("pwd") != null) {
			request.setAttribute("message", result.getFieldError("pwd").getDefaultMessage());
			return "redirect:/memberUpdateForm";
		} else if( result.getFieldError("name") != null) {
			request.setAttribute("message", result.getFieldError("name").getDefaultMessage());
			return "redirect:/memberUpdateForm";
		} else if( result.getFieldError("email") != null) {
			request.setAttribute("message", result.getFieldError("email").getDefaultMessage());
			return "redirect:/memberUpdateForm";
		} else if(pwdCheck != null && (!pwdCheck.equals(membervo.getPwd()))) {
			request.setAttribute("message", "비밀번호 확인 일치하지 않ㅅ습니다.");
			return "redirect:/memberUpdateForm";
		} else {
			membervo.setAddress(request.getParameter("addr1") + " "
					+ request.getParameter("addr2"));
			ms.updateMember(membervo);
			HttpSession session =request.getSession();
			session.setAttribute("loginUser", membervo);
			return "redirect:/";
		}	
	}
	
}
