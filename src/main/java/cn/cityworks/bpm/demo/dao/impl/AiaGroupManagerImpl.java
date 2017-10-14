package cn.cityworks.bpm.demo.dao.impl;

import com.google.common.collect.Lists;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * create by afterloe on 2017/10/14
 */
@Service
public class AiaGroupManagerImpl extends GroupEntityManager implements Serializable {

    public Group createNewGroup(String groupId) {
        return new GroupEntity(groupId);
    }

    public void insertGroup(Group group) {
//        getDbSqlSession().insert((PersistentObject) group);
        throw new RuntimeException("not implement method.");
    }

    public void updateGroup(GroupEntity updatedGroup) {
//        CommandContext commandContext = Context.getCommandContext();
//        DbSqlSession dbSqlSession = commandContext.getDbSqlSession();
//        dbSqlSession.update(updatedGroup);
        throw new RuntimeException("not implement method.");
    }

    public void deleteGroup(String groupId) {
//        GroupEntity group = getDbSqlSession().selectById(GroupEntity.class, groupId);
//        getDbSqlSession().delete("deleteMembershipsByGroupId", groupId);
//        getDbSqlSession().delete(group);
        throw new RuntimeException("not implement method.");
    }

    public GroupQuery createNewGroupQuery() {
//        return new GroupQueryImpl(Context.getProcessEngineConfiguration().getCommandExecutorTxRequired());
        throw new RuntimeException("not implement method.");
    }

    //    @SuppressWarnings("unchecked")
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
//        return getDbSqlSession().selectList("selectGroupByQueryCriteria", query, page);
        throw new RuntimeException("not implement method.");
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

    private  GroupEntity toActivitiGroup(){
//        Role role
//        if (role == null){
//            return null;
//        }
//        GroupEntity groupEntity = new GroupEntity();
//        groupEntity.setId(role.getId());
//        groupEntity.setName(role.getName());
//        groupEntity.setType(role.getType());
//        groupEntity.setRevision(1);
//        return groupEntity;
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
