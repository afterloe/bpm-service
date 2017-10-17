package cn.cityworks.bpm.dao.impl;

import cn.cityworks.bpm.exceptions.BasicException;
import cn.cityworks.bpm.integrate.UserClient;
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

    private List<Group> transformation(Map result) {
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
        Integer size = null, pageNumber = null;
        if (null != query.getId()) {
            return getNumber(userClient.getGroups(query.getId()));
        }
        String name = Optional.ofNullable(query.getName()).orElse(query.getNameLike());
        if (null != name) {
            return getNumber(userClient.listGroupsByName(name, pageNumber, size));
        }
        return getNumber(userClient.listGroups(pageNumber, size));
    }

    public List<Group> findGroupsByUser(String userId) {
        Integer size = null, pageNumber = null;
        return transformation(userClient.listGroupsByUserId(userId, pageNumber, size));
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
