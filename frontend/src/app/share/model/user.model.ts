export class User {
  private _email: String;
  private _firstname: String;
  private _lastname: String;

  constructor(email: String, fistname: String, lastname: String) {
    this._email = email;
    this._firstname = fistname;
    this._lastname = lastname;
  }

  get email(): String {
    return this._email;
  }

  get firstname(): String {
    return this._firstname;
  }

  get lastname(): String {
    return this._lastname;
  }
}
