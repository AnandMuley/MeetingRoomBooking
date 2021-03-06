package com.merobo.resources

import com.merobo.dtos.PasswordDto
import com.merobo.exceptions.UserNotFoundException
import com.merobo.services.UserService
import spock.lang.Shared
import spock.lang.Specification

import javax.ws.rs.core.Response

class UserResourceSpec extends Specification{

    @Shared
    UserResource resource

    @Shared
    UserService mockUserService

    def setup(){
        mockUserService = Mock(UserService)
        resource = new UserResource(userService: mockUserService)
    }

    def "resetPassword - should reset password successfully"(){
        given:"username of the user to reset password"
        def username = "ronnie"
        def newPwd = "abcd-123a-ew2a-23s1"
        1 * mockUserService.resetPassword(username) >> {newPwd}
        0 * _

        when:"password is reset"
        Response actual = resource.resetPassword(username)

        then:"response code 200 is returned"
        assert actual.status == Response.Status.OK.statusCode
        PasswordDto actualPwdDto = (PasswordDto)actual.getEntity()
        assert actualPwdDto.newPassword == newPwd
    }

    def "resetPassword - should return not found error if username is not found"(){
        given:"username of the user to reset password"
        def username = "dummy"
        1 * mockUserService.resetPassword(username) >> {throw new UserNotFoundException()}
        0 * _

        when:"password is reset"
        Response actual = resource.resetPassword(username)

        then:"bad request 404 error is returned"
        assert actual.status == Response.Status.NOT_FOUND.statusCode
        String message = (String)actual.entity
        assert message == "User not found"

    }

    def "resetPassword - should return internal server error if some occured"(){
        given:"username of the user to reset password"
        def username = "action"
        1 * mockUserService.resetPassword(username) >> {throw new Exception("Something went wrong")}
        0 * _

        when:"password is reset"
        Response actual = resource.resetPassword(username)

        then:"internal server error is returned"
        assert actual.status == Response.Status.INTERNAL_SERVER_ERROR.statusCode
        String message = (String)actual.entity
        assert message == "Something went wrong"
    }

}
