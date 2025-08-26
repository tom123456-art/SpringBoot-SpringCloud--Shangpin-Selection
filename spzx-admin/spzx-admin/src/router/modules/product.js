const Layout = () => import('@/layout/index.vue')
const category = () => import('@/views/product/category.vue')

export default [
  {
    path: '/product',
    component: Layout,
    name: 'product',
    meta: {
      title: '商品管理',
    },
    icon: 'Histogram',
    children: [
      {
        path: '/category',
        name: 'category',
        component: category,
        meta: {
          title: '分类管理',
        },
      },
    ],
  },
]
