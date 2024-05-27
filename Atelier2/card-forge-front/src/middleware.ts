import { RequestCookie } from "next/dist/compiled/@edge-runtime/cookies";
import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";
import { parseJwt } from "./utils/jwt";
import { IJwtPayload } from "./types/Jwt";

export function middleware(request: NextRequest) {
  const token: RequestCookie | undefined = request.cookies.get("token");

  if (!token) {
    return NextResponse.redirect(new URL("/login", request.url));
  }

  const decodedToken: IJwtPayload = parseJwt(token.value);
  const currentTime: number = Math.floor(Date.now() / 1000);
  if (decodedToken.exp < currentTime) {
    return NextResponse.redirect(new URL("/login", request.url));
  }

  return NextResponse.next();
}

export const config = {
  matcher: ["/collection", "/buy", "/sell"],
};
