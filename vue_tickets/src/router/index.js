import Vue from 'vue'   //引入Vue
import Router from 'vue-router'  //引入vue-router

import login from '@/views/login.vue'
import register from '@/views/register.vue'
import myInfo from '@/views/myInfo.vue'
import changePassword from '@/views/changePassword.vue'

import adminHome from '@/views/admin/adminHome.vue'
import flightsView from '@/components/admin/mainViews/flightsView.vue'
import schedulesView from '@/components/admin/mainViews/schedulesView.vue'
import usersView from '@/components/admin/mainViews/usersView.vue'
import ordersView from '@/components/admin/mainViews/ordersView.vue'
import messagesView from '@/components/admin/mainViews/messagesView.vue'
import passengersView from '@/components/admin/mainViews/passengersView.vue'

import userHome from '@/views/user/userHome.vue'
import userInputView from '@/components/user/mainViews/inputView.vue'
import userFlightsView from '@/components/user/mainViews/flightsView.vue'
import userFlightsAndSchedulesView from '@/components/user/mainViews/flightsAndSchedulesView.vue'
import userCheckoutView from '@/components/user/mainViews/checkoutView.vue'
import userCenterView from '@/components/user/userCenterViews/userCenterView.vue'
import userOrdersView from '@/components/user/userCenterViews/ordersView.vue'
import userBalanceView from '@/components/user/userCenterViews/balanceView.vue'
import userInfoView from '@/components/user/userCenterViews/infoView.vue'
import userRefundView from '@/components/user/userCenterViews/refundView.vue'
import userChangeView from '@/components/user/userCenterViews/changeView.vue'
import userSchedulesView from '@/components/user/mainViews/schedulesView.vue'
import userMessagesView from '@/components/user/userCenterViews/messagesView.vue'
Vue.use(Router)  //Vue全局使用Router

const router=new Router({
  mode: "history",
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/userHome',
      redirect: '/userHome/userInputView'
    },
    {
      path: '/login',
      name: 'login',
      meta: {title: '登录'},
      component: login
    },
    {
      path: '/register',
      name: 'register',
      meta: {title: '注册'},
      component: register
    },
    {
      path: '/adminHome',
      name: 'adminHome',
      meta: {title: '主页'},
      component: adminHome,
      children:[
        {
          path: 'flightsView',
          name: 'flightsView',
          meta: {title: '航班管理'},
          component: flightsView
        },
        {
          path: 'schedulesView',
          name: 'schedulesView',
          meta: {title: '调度管理'},
          component: schedulesView
        },
        {
          path: 'usersView',
          name: 'usersView',
          meta: {title: '用户管理'},
          component: usersView
        },
        {
          path: 'ordersView',
          name: 'ordersView',
          meta: {title: '订单管理'},
          component: ordersView
        },
        {
          path: 'changePassword',
          name: 'changePassword',
          meta: {title: '修改密码'},
          component: changePassword
        },
        {
          path: 'myInfo',
          name: 'myInfo',
          meta: {title: '我的信息'},
          component: myInfo
        },
        {
          path: 'messagesView',
          name: 'messagesView',
          meta: {title: '消息管理'},
          component: messagesView
        },
        {
          path: 'passengersView',
          name: 'passengersView',
          meta: {title: '乘客管理'},
          component: passengersView
        }
      ]
    },
    {
      path: '/userHome',
      name: 'userHome',
      meta: {title: '主页'},
      component: userHome,
      children:[
        {
          path: 'userFlightsView',
          name: 'userFlightsView',
          meta: {title: '航班管理'},
          component: userFlightsView
        },
        {
          path: 'userInputView',
          name: 'userInputView',
          meta: {title: '搜索页面'},
          component: userInputView
        },
        {
          path: 'userFlightsAndSchedulesView',
          name: 'userFlightsAndSchedulesView',
          meta: {title: '查看航班'},
          component: userFlightsAndSchedulesView
        },
        {
          path: 'userCheckoutView',
          name: 'userCheckoutView',
          meta: {title: '准备购票'},
          component: userCheckoutView
        },
        {
          path:'userSchedulesView',
          name: 'userSchedulesView',
          meta: {title: '航班动态'},
          component: userSchedulesView
        },
        {
          path: 'userCenterView',
          name: 'userCenterView',
          meta: {title: '用户中心'},
          component: userCenterView,
          children:[
            {
              path: 'userOrdersView',
              name: 'userOrdersView',
              meta: {title: '我的订单'},
              component: userOrdersView
            },
            {
              path: 'userBalanceView',
              name: 'userBalanceView',
              meta: {title: '我的余额'},
              component: userBalanceView
            },
            {
              path: 'userInfoView',
              name: 'userInfoView',
              meta: {title: '个人信息'},
              component: userInfoView
            },
            {
              path: 'userRefundView',
              name: 'userRefundView',
              meta: {title: '退票管理'},
              component: userRefundView
            },
            {
              path: 'userChangeView',
              name: 'userChangeView',
              meta: {title: '改签管理'},
              component: userChangeView
            },
            {
              path: 'userChangePassword',
              name: 'userChangePassword',
              meta: {title: '修改密码'},
              component: changePassword
            },
            {
              path: 'userMessagesView',
              name: 'userMessagesView',
              meta: {title: '我的消息'},
              component: userMessagesView
            }
          ]
        }
      ]
    }
  ]
})


router.beforeEach((to, from, next) => {
  if (to.path === '/login' || to.path === '/register') {
    next();
  } else {
    let token = localStorage.getItem('tickets_token');
    if (token === 'null' || token === '' || token === null) {
      if(this.$route.path === '/' || this.$route.path === '/login'){
        this.$message.error('异常');
        //do nothing
      }else{
        next('/');
      }
      
    } else {
      next();
    }
  }
});

export default router