package com.smu.stock.auth;

import com.smu.stock.controller.BaseController;
import com.smu.stock.auth.service.IAuthService;
import com.smu.stock.vo.LoginRequest;
import com.smu.stock.vo.MiscUtil;
import com.smu.stock.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

	@Autowired
	private IAuthService authService;

	/**
	 * login
	 * @param authRequest
	 * @param bindingResult
	 * @return ResponseEntity<Result>
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces="application/json")
	public ResponseEntity<Result> login(@Valid @RequestBody LoginRequest authRequest, BindingResult bindingResult) throws AuthenticationException {

		if(bindingResult.hasErrors()) {
			Result res = MiscUtil.getValidateError(bindingResult);
			return new ResponseEntity<Result>(res, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		final String token = authService.login(authRequest.getAccount(), authRequest.getPassword());

		// Return the token
		Result res = new Result(200, "ok");
		res.putData("token", token);
		return ResponseEntity.ok(res);
	}

	/**
	 * refresh
	 * @param request
	 * @return ResponseEntity<Result>
	 */
	@RequestMapping(value = "/refresh", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<Result> refresh(HttpServletRequest request, @RequestParam String token) throws AuthenticationException{

		Result res = new Result(200, "ok");

		String refreshedToken = authService.refresh(token);

		if(refreshedToken == null) {
			res.setStatus(400);
			res.setMessage("无效token");
			return new ResponseEntity<Result>(res, HttpStatus.BAD_REQUEST);
		}


		res.putData("token", token);
		return ResponseEntity.ok(res);
	}

}
