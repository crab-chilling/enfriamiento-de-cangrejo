"use client";

import * as React from "react";
import { useState, useEffect } from "react";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import Button from "@mui/material/Button";
import AddIcon from "@mui/icons-material/Add";
import TablePagination from "@mui/material/TablePagination";
import { IRoom } from "@/types/Room";
import { createGameRoom, getGameRooms, joinGameRoom } from "@/api/room";
import { useAuth } from "@/providers/AuthProvider";
import { useRouter } from "next/navigation";

export default function BuyTable() {
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [roomData, setRoomData] = useState<IRoom[]>([]);
  const { userContext } = useAuth();
  const router = useRouter();

  const handleChangePage = (event: unknown, newPage: number) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (
    event: React.ChangeEvent<HTMLInputElement>,
  ) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const paginatedData = roomData.slice(
    page * rowsPerPage,
    page * rowsPerPage + rowsPerPage,
  );

  const getRoomData = () => {
    getGameRooms()
      .then((response: IRoom[] | undefined) => {
        if (response) {
          setRoomData(response);
        }
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const handleJoinRoom = (roomId: number) => {
    if (userContext && userContext.id) {
      joinGameRoom(roomId, userContext.id)
        .then((response: IRoom | undefined) => {
          console.log("Room joined:", response);
        })
        .catch((error) => {
          console.error("Error joining room:", error);
        });
    } else {
      console.error("User not authenticated");
    }
  };

  const handleCreateRoom = () => {
    router.push("/rooms/create");
  };

  useEffect(() => {
    getRoomData();
  }, []);

  return (
    <Container className="mt-5">
      <Box className="flex justify-center items-center mb-5">
        <Typography variant="h4" component="h1" className="mr-2">
          Salons de jeu
        </Typography>
        <Typography variant="subtitle1" component="p" className="italic">
          ({roomData.length} salons ouverts)
        </Typography>
      </Box>
      <Box sx={{ display: "flex" }}>
        <Box sx={{ flexGrow: 1, mr: 0 }}>
          <TableContainer component={Paper}>
            <Table
              sx={{ minWidth: 650 }}
              aria-label="simple table"
              size="small"
            >
              <TableHead>
                <TableRow sx={{ backgroundColor: "#2196f3" }}>
                  <TableCell className="text-white">Nom du salon</TableCell>
                  <TableCell align="right" className="text-white">
                    Mise
                  </TableCell>
                  <TableCell align="center" className="text-white">
                    Actions
                  </TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {paginatedData.map((room) => (
                  <TableRow key={room.id} hover sx={{ cursor: "pointer" }}>
                    <TableCell component="th" scope="row">
                      {room.name}
                    </TableCell>
                    <TableCell align="right">{room.bet} €</TableCell>
                    <TableCell align="center">
                      <Button
                        variant="contained"
                        color="primary"
                        onClick={() => handleJoinRoom(room.id)}
                      >
                        Rejoindre
                      </Button>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
            <TablePagination
              rowsPerPageOptions={[5, 10, 25]}
              component="div"
              count={roomData.length}
              rowsPerPage={rowsPerPage}
              page={page}
              onPageChange={handleChangePage}
              onRowsPerPageChange={handleChangeRowsPerPage}
            />
          </TableContainer>
          <Box sx={{ display: "flex", justifyContent: "start", mt: 2 }}>
            <Button
              startIcon={<AddIcon />}
              variant="contained"
              color="primary"
              onClick={handleCreateRoom}
            >
              Créer un nouveau salon
            </Button>
          </Box>
        </Box>
      </Box>
    </Container>
  );
}
