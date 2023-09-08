import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { HomeComponent } from "./home/home.component";
import { LoginComponent } from "./auth/login/login.component";
import { AuthGuard } from "./auth/auth.guard";
import { RegisterComponent } from "./auth/register/register.component";
import { MainComponent } from "./layout/main/main.component";
import { InvestmentComponent } from "./investment/investment.component";
import { TransactionHistoryComponent } from "./transaction-history/transaction-history.component";

const routes: Routes = [
  { path: "", redirectTo: "main", pathMatch: "full" },
  {
    path: "main",
    component: MainComponent,
    canActivate: [AuthGuard],
    children: [
      { path: "", redirectTo: "home", pathMatch: "full" },
      { path: "home", component: HomeComponent, canActivate: [AuthGuard] },
      {
        path: "investments",
        component: InvestmentComponent,
        canActivate: [AuthGuard],
      },
      {
        path: "transaction-history",
        component: TransactionHistoryComponent,
        canActivate: [AuthGuard],
      },
    ],
  },
  { path: "login", component: LoginComponent },
  { path: "register", component: RegisterComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
