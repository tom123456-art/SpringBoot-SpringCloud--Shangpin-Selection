import request from '@/utils/request'

export const ChatAi = data => {
  return request({
    url: '/admin/ai/chat',
    method: 'post',
    data,
  })
}
