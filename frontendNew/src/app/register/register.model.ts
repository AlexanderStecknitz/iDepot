export class Register {
  private email: string;
  private password: string;
  private firstname: string;
  private lastname: string;

  constructor(email: string, password: string, firstname: string, lastname: string) {
    this.email = email;
    this.password = password;
    this.firstname = firstname;
    this.lastname = lastname;
  }

}
