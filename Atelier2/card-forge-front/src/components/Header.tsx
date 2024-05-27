"use client";

import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { Button } from "@mui/material";
import WhatshotIcon from "@mui/icons-material/Whatshot";
import { useRouter } from "next/navigation";
import { useAuth } from "@/providers/AuthProvider";

export default function ResponsiveAppBar() {
  const router = useRouter();
  const { isLoggedIn, userContext, logout } = useAuth();

  return (
    <AppBar position="static">
      <Container maxWidth="xl">
        <Toolbar disableGutters sx={{ justifyContent: "space-between" }}>
          <div
            className="flex flex-row items-center cursor-pointer"
            onClick={() => router.push("/")}
          >
            <WhatshotIcon sx={{ display: { xs: "none", md: "flex" }, mr: 1 }} />
            <Typography
              variant="h6"
              noWrap
              component="a"
              sx={{
                mr: 2,
                display: { xs: "none", md: "flex" },
                fontWeight: 700,
                color: "inherit",
                textDecoration: "none",
              }}
            >
              Card Forge
            </Typography>
          </div>

          {isLoggedIn ? (
            <div style={{ display: "flex", alignItems: "center" }}>
              {isLoggedIn && userContext && (
                <div
                  className="flex flex-row items-center"
                  style={{ marginRight: "20px" }}
                >
                  <Typography variant="body1" style={{ marginLeft: "10px" }}>
                    {userContext.firstName} {userContext.lastName} (
                    {userContext.wallet} €)
                  </Typography>
                </div>
              )}
              <Button
                variant="outlined"
                sx={{ backgroundColor: "white" }}
                onClick={logout}
              >
                Se déconnecter
              </Button>
            </div>
          ) : (
            <Button
              variant="outlined"
              sx={{ backgroundColor: "white" }}
              onClick={() => router.push("/login")}
            >
              Se connecter
            </Button>
          )}
        </Toolbar>
      </Container>
    </AppBar>
  );
}
