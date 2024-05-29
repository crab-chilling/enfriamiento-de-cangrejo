"use client";

import * as React from "react";
import { useState } from "react";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { Alert } from "@mui/material";
import { useAuth } from "@/providers/AuthProvider";
import { createGameRoom } from "@/api/room";
import { IRoom } from "@/types/Room";
import { useRouter } from "next/navigation";

export default function CreateRoomForm() {
  const [name, setName] = useState<string>("");
  const [bet, setBet] = useState<number>(0);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const { userContext } = useAuth();
  const router = useRouter();

  const handleCreateRoom = async (event: React.FormEvent) => {
    event.preventDefault();
    if (!name) {
      setErrorMessage("Le nom du salon n'est pas valide.");
      return;
    }

    if (bet <= 0) {
      setErrorMessage("Le montant de la mise n'est pas valide.");
      return;
    }

    if (userContext && userContext.wallet < bet) {
      setErrorMessage("Vous ne disposez pas des fonds nécessaires.");
      return;
    }

    const newRoom: Partial<IRoom> = { name, bet };

    try {
      const createdRoom = await createGameRoom(newRoom);
      if (createdRoom) {
        setErrorMessage(null);
        router.push("/rooms/" + createdRoom.id);
      } else {
        setErrorMessage(
          "Une erreur est survenue lors de la création du salon.",
        );
      }
    } catch (error) {
      console.error("Error creating room:", error);
      setErrorMessage("Une erreur est survenue lors de la création du salon.");
    }
  };

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <Box
        sx={{
          marginTop: 8,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <Typography component="h1" variant="h5">
          Créér un nouveau salon
        </Typography>
        <Box
          component="form"
          onSubmit={handleCreateRoom}
          noValidate
          sx={{ mt: 4 }}
        >
          {errorMessage && <Alert severity="error">{errorMessage}</Alert>}
          <TextField
            required
            id="name"
            label="Nom du salon"
            variant="outlined"
            fullWidth
            value={name}
            onChange={(e) => setName(e.target.value)}
            margin="normal"
          />
          <TextField
            required
            id="bet"
            label="Mise (€)"
            type="number"
            variant="outlined"
            fullWidth
            value={bet}
            onChange={(e) => setBet(parseFloat(e.target.value))}
            margin="normal"
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
          >
            Créér le salon
          </Button>
        </Box>
      </Box>
    </Container>
  );
}
