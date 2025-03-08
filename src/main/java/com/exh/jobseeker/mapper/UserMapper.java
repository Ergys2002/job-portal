package com.exh.jobseeker.mapper;

import com.exh.jobseeker.model.dto.response.UserResponse;
import com.exh.jobseeker.model.entity.User;
import com.exh.jobseeker.model.entity.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "userInfo.phoneNumber", target = "phoneNumber")
    @Mapping(source = "userInfo.firstName", target = "firstName")
    @Mapping(source = "userInfo.lastName", target = "lastName")
    @Mapping(source = "user.role", target = "role")
    UserResponse toUserResponse(User user, UserInfo userInfo);

    default UserResponse userToUserResponse(User user) {
        if (user == null) {
            return null;
        }
        return toUserResponse(user, user.getUserInfo());
    }

    default List<UserResponse> usersToUserResponses(List<User> users) {
        if (users == null) {
            return null;
        }
        return users.stream()
                .map(this::userToUserResponse)
                .toList();
    }
}
