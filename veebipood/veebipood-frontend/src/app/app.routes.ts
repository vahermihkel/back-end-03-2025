import { Routes } from '@angular/router';
import { CartComponent } from './cart/cart.component';
import { HomepageComponent } from './homepage/homepage.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { AdminHomeComponent } from './admin/admin-home/admin-home.component';
import { AddProductComponent } from './admin/add-product/add-product.component';
import { ManageProductsComponent } from './admin/manage-products/manage-products.component';
import { AddProduct2Component } from './admin/add-product-2/add-product-2.component';
import { AddProduct3Component } from './admin/add-product-3/add-product-3.component';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { OrdersComponent } from './orders/orders.component';
import { ManageAdminsComponent } from './admin/manage-admins/manage-admins.component';
import { authGuard } from './guards/auth.guard';

// localhost:4200/cart   ---> cart.component.html

export const routes: Routes = [
  {path: "", component: HomepageComponent},
  {path: "cart", component: CartComponent},
  {path: "login", component: LoginComponent},
  {path: "signup", component: SignupComponent},
  {path: "orders", component: OrdersComponent},

  // {path: "admin", component: AdminHomeComponent, canActivate: [auth2Guard]},
  // {path: "admin/add-product", component: AddProductComponent, canActivate: [auth2Guard]},
  // {path: "admin/add-product-2", component: AddProduct2Component, canActivate: [auth2Guard]},
  // {path: "admin/add-product-3", component: AddProduct3Component, canActivate: [auth2Guard]},
  // {path: "admin/manage-products", component: ManageProductsComponent, canActivate: [auth2Guard]},
  // {path: "admin/manage-admins", component: ManageAdminsComponent, canActivate: [auth2Guard]},

  {path: "admin", canActivate: [authGuard], component: AdminHomeComponent},
  {path: "admin", canActivateChild: [authGuard], children: [
    {path: "add-product", component: AddProductComponent},
    {path: "add-product-2", component: AddProduct2Component},
    {path: "add-product-3", component: AddProduct3Component},
    {path: "manage-products", component: ManageProductsComponent},
    {path: "manage-admins", component: ManageAdminsComponent},
  ]},
 

  {path: "**", component: NotFoundComponent},
];
