import{_ as f,c as d,w as p,a as e,b as r,v as a,d as m,e as c,F as h,o as g}from"./index-CtvjVp6e.js";const x={inject:["axios"],data(){return{register:{username:"",email:"",phoneNumber:"",password:""},login:{username:"",password:""},loginType:"signIn"}},methods:{handleRegister(){this.loginType="signUp"},handleLogin(){this.loginType="signIn"},userRegister(){this.axios.post("auth/sign-up",{...this.register}).then(n=>{var t,i;(t=n.data)!=null&&t.token&&(localStorage.setItem("token",JSON.stringify((i=n.data)==null?void 0:i.token)),JSON.parse(localStorage.getItem("token"))&&(this.$toast.open({message:"Успешно  авторизирован!",type:"success",position:"top-right"}),this.$router.push("/history")))}).catch(n=>{this.$toast.open({message:"Что-то пошло не так",type:"error",position:"top-right"})})},userLogin(){this.axios.post("auth/sign-in",{...this.login}).then(n=>{var t,i;(t=n.data)!=null&&t.token&&(localStorage.setItem("token",JSON.stringify((i=n.data)==null?void 0:i.token)),JSON.parse(localStorage.getItem("token"))&&(this.$toast.open({message:"Успешно  авторизирован!",type:"success",position:"top-right"}),this.$router.push("/history")))}).catch(n=>{this.$toast.open({message:"Что-то пошло не так",type:"error",position:"top-right"})})}}},_=e("h1",{class:"login__logo text-4xl font-semibold text-center"},"Войти",-1),b={class:"login__inputs mt-12 flex flex-col gap-6"},y={class:"login__inputs-block flex flex-col gap-3"},k=e("label",{for:"login",class:"font-semibold"}," Имя пользователя",-1),w={class:"login__inputs-block flex flex-col gap-3"},v=e("label",{for:"login",class:"font-semibold"},"Пароль",-1),N=e("button",{type:"submit",class:"login__submit mt-12 w-full bg-white text-black flex justify-center items-center h-16 rounded-lg font-semibold text-xl"}," Войти ",-1),S={class:"mt-8 text-center"},V={class:"text-sm"},U=e("h1",{class:"login__logo text-4xl font-semibold text-center"},"Регистрация",-1),I={class:"login__inputs mt-12 flex flex-col gap-6"},T={class:"login__inputs-block flex flex-col gap-3"},L=e("label",{for:"login",class:"font-semibold"}," Имя пользователя",-1),R={class:"login__inputs-block flex flex-col gap-3"},q=e("label",{for:"login",class:"font-semibold"},"Email",-1),J={class:"login__inputs-block flex flex-col gap-3"},O=e("label",{for:"login",class:"font-semibold"},"Номер телефона",-1),j={class:"login__inputs-block flex flex-col gap-3"},B=e("label",{for:"login",class:"font-semibold"},"Пароль",-1),C=e("button",{type:"submit",class:"login__submit mt-12 w-full bg-white text-black flex justify-center items-center h-16 rounded-lg font-semibold text-xl"}," Зарегистрироваться ",-1),E={class:"mt-8 text-center"},F={class:"text-sm"};function M(n,t,i,u,o,l){return g(),d(h,null,[o.loginType==="signIn"?(g(),d("form",{key:0,onSubmit:t[3]||(t[3]=p((...s)=>l.userLogin&&l.userLogin(...s),["prevent"])),class:"login w-420 mx-auto my-auto p-6 shadow-xl bg-dark-gray text-white absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 rounded-md"},[_,e("div",b,[e("div",y,[k,r(e("input",{required:"","onUpdate:modelValue":t[0]||(t[0]=s=>o.login.username=s),type:"text",class:"outline-none h-14 rounded-md bg-gray p-4"},null,512),[[a,o.login.username]])]),e("div",w,[v,r(e("input",{required:"","onUpdate:modelValue":t[1]||(t[1]=s=>o.login.password=s),type:"password",class:"outline-none h-14 rounded-md bg-gray p-4"},null,512),[[a,o.login.password]])])]),N,e("div",S,[e("p",V,[m(" У вас нет аккаунта? "),e("span",{class:"text-sky-500 font-semibold cursor-pointer",onClick:t[2]||(t[2]=(...s)=>l.handleRegister&&l.handleRegister(...s))},"Зарегистрироваться")])])],32)):c("",!0),o.loginType==="signUp"?(g(),d("form",{key:1,onSubmit:t[9]||(t[9]=p((...s)=>l.userRegister&&l.userRegister(...s),["prevent"])),class:"login w-550 mx-auto my-auto p-6 shadow-xl bg-dark-gray text-white absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 rounded-md"},[U,e("div",I,[e("div",T,[L,r(e("input",{required:"","onUpdate:modelValue":t[4]||(t[4]=s=>o.register.username=s),type:"text",class:"outline-none h-14 rounded-md bg-gray p-4"},null,512),[[a,o.register.username]])]),e("div",R,[q,r(e("input",{required:"","onUpdate:modelValue":t[5]||(t[5]=s=>o.register.email=s),type:"text",class:"outline-none h-14 rounded-md bg-gray p-4"},null,512),[[a,o.register.email]])]),e("div",J,[O,r(e("input",{"onUpdate:modelValue":t[6]||(t[6]=s=>o.register.phoneNumber=s),type:"text",class:"outline-none h-14 rounded-md bg-gray p-4"},null,512),[[a,o.register.phoneNumber]])]),e("div",j,[B,r(e("input",{required:"","onUpdate:modelValue":t[7]||(t[7]=s=>o.register.password=s),type:"password",class:"outline-none h-14 rounded-md bg-gray p-4"},null,512),[[a,o.register.password]])])]),C,e("div",E,[e("p",F,[m(" Уже есть аккаунт? "),e("span",{class:"text-sky-500 font-semibold cursor-pointer",onClick:t[8]||(t[8]=(...s)=>l.handleLogin&&l.handleLogin(...s))},"Войти")])])],32)):c("",!0)],64)}const z=f(x,[["render",M]]);export{z as default};
