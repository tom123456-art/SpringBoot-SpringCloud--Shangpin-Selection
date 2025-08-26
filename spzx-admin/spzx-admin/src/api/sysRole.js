import request from '@/utils/request'
const base_api = '/admin/system/sysRole'
// 分页查询角色数据
export const GetSysRoleListByPage = (current, limit, queryDto) => {
  return request({
    url: `${base_api}/findByPage/${current}/${limit}`,
    method: 'post',
    data: queryDto,
  })
}

// 添加角色请求方法
export const SaveSysRole = ( sysRole ) => {
  return request({
    url: `${base_api}/saveSysRole`,
    method: 'post',
    data: sysRole,
  })
}

//角色修改
export const UpdateSysRole = ( sysRole ) => {
  return request({
    url: `${base_api}/updateSysRole`,
    method: 'put',
    data: sysRole,
  })
}

//角色删除
export const DeleteSysRole = ( roleId ) => {
  return request({
    url: `${base_api}/deleteById/${roleId}`,
    method: 'delete',
  })
}

// 查询所有的角色数据
export const GetAllRoleList = (userId) => {
    return request({
        url: `/admin/system/sysRole/findAllRoles/${userId}`,
        method: 'get'
    })
}


