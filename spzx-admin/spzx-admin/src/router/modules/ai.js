const Layout = () => import('@/layout/index.vue')
const AiChat = () => import('@/views/ai/index.vue')

export default [
  {
    path: '/ai',
    component: Layout,
    name: 'ai',
    meta: {
      title: 'AI助手',
    },
    icon: 'ChatDotRound',
    children: [
      {
        path: '/aiChat',
        name: 'aiChat',
        component: AiChat,
        meta: {
          title: '智能问答',
        },
        hidden: false,
      },
    ],
  },
]
