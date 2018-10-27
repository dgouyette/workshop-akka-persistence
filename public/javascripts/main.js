const routes = [
    {path: '/', component: DashboardList},
    {path: '/show/:id', component: DashboardShow},
];
const router = new VueRouter({
    routes // short for `routes: routes`
});
const vm = new Vue({
    el: '#app',
    router,
    created() {
        console.log("vm created");
    }
});
