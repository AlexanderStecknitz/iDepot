export class Login {
  username: string;
  password: string;

  constructor(username: string, password: string) {
    this.username = username;
    this.password = password;
  }
}

export interface Registration {
  firstname: string,
  lastname: string,
  email: string,
  password: string,
  role: string,
}
