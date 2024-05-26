export interface IUserLoginResponse {
  firstName: string;
  lastName: string;
  username: string;
  emailAddress: string;
  wallet: number;
}

export interface IUserLoginBody {
  emailAddress: string;
  password: string;
}

export interface IUserRegisterBody {
  firstName: string;
  lastName: string;
  emailAddress: string;
  password: string;
}
