package cn.cityworks.bpm.demo.dao.impl;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AiaUserManagerImpl extends UserEntityManager {

    public User createNewUser(String userId) {
        return new UserEntity(userId);
    }

    public void insertUser(User user) {
//      getDbSqlSession().insert((PersistentObject) user);
        throw new RuntimeException("not implement method.");
    }

    public void updateUser(UserEntity updatedUser) {
//      CommandContext commandContext = Context.getCommandContext();
//      DbSqlSession dbSqlSession = commandContext.getDbSqlSession();
//      dbSqlSession.update(updatedUser);
        throw new RuntimeException("not implement method.");
    }

    public UserEntity findUserById(String userId) {
//      return (UserEntity) getDbSqlSession().selectOne("selectUserById", userId);
//        return toActivitiUser(userService.obtainUser(userId));
        throw new RuntimeException("not implement method.");
    }

    private UserEntity toActivitiUser(){
//        User user
//        if (user == null){
//            return null;
//        }
//        UserEntity userEntity = new UserEntity();
//        userEntity.setId(user.getLoginName());
//        userEntity.setFirstName(user.getName());
//        userEntity.setLastName(StringUtils.EMPTY);
//        userEntity.setPassword(user.getPassword());
//        userEntity.setEmail(user.getEmail());
//        userEntity.setRevision(1);
        throw new RuntimeException("not implement method.");
    }

    public void deleteUser(String userId) {
//      UserEntity user = findUserById(userId);
//      if (user != null) {
//          List<IdentityInfoEntity> identityInfos = getDbSqlSession().selectList("selectIdentityInfoByUserId", userId);
//          for (IdentityInfoEntity identityInfo : identityInfos) {
//              getIdentityInfoManager().deleteIdentityInfo(identityInfo);
//          }
//          getDbSqlSession().delete("deleteMembershipsByUserId", userId);
//          user.delete();
//      }
        throw new RuntimeException("not implement method.");
    }

    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
//      return getDbSqlSession().selectList("selectUserByQueryCriteria", query, page);
        throw new RuntimeException("not implement method.");
    }

    public long findUserCountByQueryCriteria(UserQueryImpl query) {
//      return (Long) getDbSqlSession().selectOne("selectUserCountByQueryCriteria", query);
        throw new RuntimeException("not implement method.");
    }

    public List<Group> findGroupsByUser(String userId) {
//      return getDbSqlSession().selectList("selectGroupsByUserId", userId);
        throw new RuntimeException("not implement method.");
    }

    private GroupEntity toActivitiGroup(){
        throw new RuntimeException("not implement method.");
    }

    public UserQuery createNewUserQuery() {
//      return new UserQueryImpl(Context.getProcessEngineConfiguration().getCommandExecutorTxRequired());
        throw new RuntimeException("not implement method.");
    }

    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId, String key) {
//      Map<String, String> parameters = new HashMap<String, String>();
//      parameters.put("userId", userId);
//      parameters.put("key", key);
//      return (IdentityInfoEntity) getDbSqlSession().selectOne("selectIdentityInfoByUserIdAndKey", parameters);
        throw new RuntimeException("not implement method.");
    }

    public List<String> findUserInfoKeysByUserIdAndType(String userId, String type) {
//      Map<String, String> parameters = new HashMap<String, String>();
//      parameters.put("userId", userId);
//      parameters.put("type", type);
//      return (List) getDbSqlSession().getSqlSession().selectList("selectIdentityInfoKeysByUserIdAndType", parameters);
        throw new RuntimeException("not implement method.");
    }

    public Boolean checkPassword(String userId, String password) {
//      User user = findUserById(userId);
//      if ((user != null) && (password != null) && (password.equals(user.getPassword()))) {
//          return true;
//      }
//      return false;
        throw new RuntimeException("not implement method.");
    }

    public List<User> findPotentialStarterUsers(String proceDefId) {
//      Map<String, String> parameters = new HashMap<String, String>();
//      parameters.put("procDefId", proceDefId);
//      return (List<User>) getDbSqlSession().selectOne("selectUserByQueryCriteria", parameters);
        throw new RuntimeException("not implement method.");

    }

    public List<User> findUsersByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
//      return getDbSqlSession().selectListWithRawParameter("selectUserByNativeQuery", parameterMap, firstResult, maxResults);
        throw new RuntimeException("not implement method.");
    }

    public long findUserCountByNativeQuery(Map<String, Object> parameterMap) {
//      return (Long) getDbSqlSession().selectOne("selectUserCountByNativeQuery", parameterMap);
        throw new RuntimeException("not implement method.");
    }
}
