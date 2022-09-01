package site.metacoding.red.web;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.domain.users.UsersDao;
import site.metacoding.red.web.dto.request.users.JoinDto;
import site.metacoding.red.web.dto.request.users.UpdateDto;
import site.metacoding.red.web.dto.response.RespDto;

@RequiredArgsConstructor
@RestController
public class UsersController {

	private final UsersDao usersDao; // final 붙은 녀석을 생성자 만들어주려면 @Required~만들어줘야함

	@GetMapping("/users/{id}")
	public RespDto<?> getUsers(@PathVariable Integer id) {
		return new RespDto<>(1, "성공", usersDao.findById(id));
	}

	@GetMapping("/users")
	public RespDto<?> getUserList() {
		return new RespDto<>(1, "성공", usersDao.findAll());
	}

	@PostMapping("/users")
	public RespDto<?> insert(JoinDto joinDto) {
		usersDao.insert(joinDto);
		return new RespDto<>(1, "가입완료", null);
	}

	@PutMapping("/users/{id}")
	public RespDto<?> update(@PathVariable Integer id, UpdateDto updateDto) {
		// 1번 : id로 select 하자. (영속화)
		Users usersPS = usersDao.findById(id);

		// 2번 : 변경
		usersPS.전체수정(updateDto);

		// 3번 : 영속화된 오브젝트로 update하기
		usersDao.update(usersPS);
		return new RespDto<>(1, "수정완료", "변경된 유저네임은 : " + updateDto.getUsername() + ", 변경된 비밀번호는 : "
				+ updateDto.getPassword() + ", 변경된 이메일은 : " + updateDto.getEmail());
	}

	@DeleteMapping("/users/{id}")
	public RespDto<?> delete(@PathVariable Integer id) {
		usersDao.delete(id);
		return new RespDto<>(1, "탈퇴완료", "탈퇴된 아이디는 : " + id);
	}

	@PutMapping("/users/{id}/password")
	public RespDto<?> updatePassword(@PathVariable Integer id, String password) {
		Users usersPS = usersDao.findById(id);

		usersPS.패스워드수정(password);

		usersDao.update(usersPS);

		return new RespDto<>(1, "수정완료", null);
	}
}
