import request from '@/utils/request'

const api_name = '/admin/product/category'

// 根据parentId获取下级节点
export const FindCategoryByParentId = id => {
  return request({
    url: `${api_name}/findCategoryList/${id}`,
    method: 'get',
  })
}