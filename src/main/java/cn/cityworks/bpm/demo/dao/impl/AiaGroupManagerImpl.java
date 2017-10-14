package cn.cityworks.bpm.demo.dao.impl;

import cn.cityworks.bpm.demo.exceptions.BasicException;
import cn.cityworks.bpm.demo.integrate.UserClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * create by afterloe on 2017/10/14
 */
@Service
public class AiaGroupManagerImpl extends GroupEntityManager implements Serializable {

    @Autowired
    private UserClient userClient;

    public Group createNewGroup(String groupId) {
        return new GroupEntity(groupId);
    }

    public void insertGroup(Group group) {
        throw BasicException.build("can't create user by this service. Please use user-maneger in soa"
                , HttpStatus.SC_UNAUTHORIZED);
    }

    public void updateGroup(GroupEntity updatedGroup) {
        throw BasicException.build("can't create user by this service. Please use user-maneger in soa"
                , HttpStatus.SC_UNAUTHORIZED);
    }

    public void deleteGroup(String groupId) {
        throw BasicException.build("can't create user by this service. Please use user-maneger in soa"
                , HttpStatus.SC_UNAUTHORIZED);
    }

    public GroupQuery createNewGroupQuery() {
        throw BasicException.build("can't create user by this service. Please use user-maneger in soa"
                , HttpStatus.SC_UNAUTHORIZED);
    }

    private List<Group> transformation(Map result) {
        Map pageObject = (Map) result.get("data");
        List<Map> content = (List) pageObject.get("content");
        return content.stream().map(obj -> {
            GroupEntity group = new GroupEntity();
            group.setId(obj.get("id").toString());
            group.setName(obj.get("groupName").toString());
            group.setType(obj.get("active").toString());
            group.setRevision(1);
            return group;
        }).collect(toList());
    }

    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
        Integer size = null, pageNumber = null;
        if (null != page) {
            int begin = page.getFirstResult();
            size = page.getMaxResults();
            pageNumber = begin % size;
        }
        if (null != query.getId()) {
            return transformation(userClient.getGroups(query.getId()));
        }
        String name = Optional.ofNullable(query.getName()).orElse(query.getNameLike());
        if (null != name) {
            return transformation(userClient.listGroupsByName(name, pageNumber, size));
        }
        return transformation(userClient.listGroups(pageNumber, size));
    }

    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
//        return (Long) getDbSqlSession().selectOne("selectGroupCountByQueryCriteria", query);
        throw new RuntimeException("not implement method.");
    }

    public List<Group> findGroupsByUser(String userId) {
//        return getDbSqlSession().selectList("selectGroupsByUserId", userId);
        List<Group> list = Lists.newArrayList();
//        User user = userService.obtainUser(userId);
//        if (user != null){
//            List<Role> roles=roleService.obtainRoles(userId);
//            for (Role role : roles){
//                list.add(toActivitiGroup(role));
//            }
//        }
        throw new RuntimeException("not implement method.");
    }

    public List<Group> findGroupsByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
//        return getDbSqlSession().selectListWithRawParameter("selectGroupByNativeQuery", parameterMap, firstResult, maxResults);
        throw new RuntimeException("not implement method.");
    }

    public long findGroupCountByNativeQuery(Map<String, Object> parameterMap) {
//        return (Long) getDbSqlSession().selectOne("selectGroupCountByNativeQuery", parameterMap);
        throw new RuntimeException("not implement method.");
    }

}
