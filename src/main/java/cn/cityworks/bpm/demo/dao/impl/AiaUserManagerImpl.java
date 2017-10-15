package cn.cityworks.bpm.demo.dao.impl;

import cn.cityworks.bpm.demo.exceptions.BasicException;
import cn.cityworks.bpm.demo.integrate.UserClient;
import com.google.common.collect.Lists;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class AiaUserManagerImpl extends UserEntityManager {

    @Autowired
    private UserClient userClient;

    public User createNewUser(String userId) {
        return new UserEntity(userId);
    }

    public void insertUser(User user) {
        throw BasicException.build("can't create user by this service. Please use user-maneger in soa"
                , HttpStatus.SC_UNAUTHORIZED);
    }

    public void deleteUser(String userId) {
        throw BasicException.build("can't create user by this service. Please use user-maneger in soa"
                , HttpStatus.SC_UNAUTHORIZED);
    }

    public void updateUser(UserEntity updatedUser) {
        throw BasicException.build("can't create user by this service. Please use user-maneger in soa"
                , HttpStatus.SC_UNAUTHORIZED);
    }

    public UserEntity findUserById(String userId) {
        Map result = userClient.getUserById(userId);
        if (HttpStatus.SC_OK != Integer.valueOf(result.get("code").toString())) {
            return null;
        }
        Map data = (Map) result.get("data");
        UserEntity user = new UserEntity();
        user.setId(data.get("id").toString());
        user.setRevision(1);
        user.setFirstName(data.get("username").toString());
        user.setLastName(data.get("nickname").toString());
        user.setEmail(data.get("email").toString());
        return user;
    }

    private long getNumber(Map result) {
        if (HttpStatus.SC_OK != Integer.valueOf(result.get("code").toString())) {
            return 0l;
        }
        Map pageObject = (Map) result.get("data");
        Long value = 0l;
        try {
            value = (Long) pageObject.get("totalElements");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return value;
    }

    private List<Group> transformationToGroup(Map result) {
        if (HttpStatus.SC_OK != Integer.valueOf(result.get("code").toString())) {
            return Lists.newArrayList();
        }
        Map data = (Map) result.get("data");
        if (data.containsKey("content")) {
            List<Map> content = (List) data.get("content");
            return content.stream().map(obj -> {
                GroupEntity group = new GroupEntity();
                group.setId(obj.get("id").toString());
                group.setName(obj.get("groupName").toString());
                group.setType(obj.get("active").toString());
                group.setRevision(1);
                return group;
            }).collect(toList());
        }
        GroupEntity group = new GroupEntity();
        group.setId(data.get("id").toString());
        group.setName(data.get("groupName").toString());
        group.setType(data.get("active").toString());
        group.setRevision(1);

        return Lists.newArrayList(group);
    }

    private List<User> transformation(Map result) {
        if (HttpStatus.SC_OK != Integer.valueOf(result.get("code").toString())) {
            return Lists.newArrayList();
        }
        Map data = (Map) result.get("data");
        if (data.containsKey("content")) {
            List<Map> content = (List) data.get("content");
            return content.stream().map(obj -> {
                UserEntity user = new UserEntity();
                user.setId(data.get("id").toString());
                user.setRevision(1);
                user.setFirstName(data.get("username").toString());
                user.setLastName(data.get("nickname").toString());
                user.setEmail(data.get("email").toString());
                return user;
            }).collect(toList());
        }

        return null;
    }

    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
        Integer size = null, pageNumber = null;
        if (null != page) {
            int begin = page.getFirstResult();
            size = page.getMaxResults();
            pageNumber = begin % size;
        }
        if (null != query.getId()) {
            return transformation(userClient.getUserById(query.getId()));
        }
        String name = Optional.ofNullable(query.getFirstName()).orElse(query.getFirstNameLike());
        if (null != name) {
            return transformation(userClient.listUsersByUsername(name, pageNumber, size));
        }
        if (null != query.getGroupId()) {
            return transformation(userClient.listUsersByGroupId(query.getGroupId(), pageNumber, size));
        }

        return transformation(userClient.listUsers(pageNumber, size));
    }

    public long findUserCountByQueryCriteria(UserQueryImpl query) {
        Integer size = null, pageNumber = null;
        if (null != query.getId()) {
            return getNumber(userClient.getUserById(query.getId()));
        }
        String name = Optional.ofNullable(query.getFirstName()).orElse(query.getFirstNameLike());
        if (null != name) {
            return getNumber(userClient.listUsersByUsername(name, pageNumber, size));
        }
        if (null != query.getGroupId()) {
            return getNumber(userClient.listUsersByGroupId(query.getGroupId(), pageNumber, size));
        }

        return getNumber(userClient.listUsers(pageNumber, size));
    }

    public List<Group> findGroupsByUser(String userId) {
        Integer size = null, pageNumber = null;
        return transformationToGroup(userClient.listGroupsByUserId(userId, pageNumber, size));
    }

    public UserQuery createNewUserQuery() {
//      return new UserQueryImpl(Context.getProcessEngineConfiguration().getCommandExecutorTxRequired());
        throw new RuntimeException("not implement method.");
    }

    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId, String key) {
        Map result = userClient.getUserById(userId);
        IdentityInfoEntity identityInfoEntity = new IdentityInfoEntity();
        identityInfoEntity.setUserId(userId);
        identityInfoEntity.setDetails((Map<String, String>) result.get("data"));
        return identityInfoEntity;
    }

    public List<String> findUserInfoKeysByUserIdAndType(String userId, String type) {
        throw new RuntimeException("not implement method.");
    }

    public Boolean checkPassword(String userId, String password) {
        throw new RuntimeException("not implement method.");
    }

    public List<User> findPotentialStarterUsers(String proceDefId) {
        throw new RuntimeException("not implement method.");
    }

    public List<User> findUsersByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
        throw new RuntimeException("not implement method.");
    }

    public long findUserCountByNativeQuery(Map<String, Object> parameterMap) {
        throw new RuntimeException("not implement method.");
    }
}
