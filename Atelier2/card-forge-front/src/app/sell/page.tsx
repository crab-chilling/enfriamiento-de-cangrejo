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
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Grid from "@mui/material/Grid";
import Container from "@mui/material/Container";
import SellIcon from "@mui/icons-material/Sell";
import Button from "@mui/material/Button";
import TablePagination from "@mui/material/TablePagination";
import { IMarketCard, IUser, IUserCard } from "@/types/Card";
import { TextField, InputAdornment } from "@mui/material";
import OfflineBoltRoundedIcon from "@mui/icons-material/OfflineBoltRounded";
import FavoriteRoundedIcon from "@mui/icons-material/FavoriteRounded";
import ShieldRoundedIcon from "@mui/icons-material/ShieldRounded";
import SportsMmaRoundedIcon from "@mui/icons-material/SportsMmaRounded";
import { getUserCardCollection } from "@/api/card";
import { useAuth } from "@/providers/AuthProvider";
import { sell } from "@/api/market";

export default function SellTable() {
  const { user } = useAuth();
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [selectedCard, setSelectedCard] = useState<IUserCard | null>(null);
  const [sellingPrice, setSellingPrice] = useState<number | null>(null);

  const handlePriceChange = (event) => {
    const value = event.target.value;
    if (value === "" || (Number(value) > 0 && !isNaN(Number(value)))) {
      setSellingPrice(event.target.value);
    }
  };

  const handleChangePage = (event: unknown, newPage: number) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (
    event: React.ChangeEvent<HTMLInputElement>,
  ) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const handleRowClick = (card: IUserCard) => {
    setSelectedCard(card);
  };

  const [cardData, setCardData] = useState<IUserCard[]>([]);

  const paginatedData = cardData.slice(
    page * rowsPerPage,
    page * rowsPerPage + rowsPerPage,
  );

  const sellSelectedCard = () => {
    if (sellingPrice && selectedCard) {
      sell(sellingPrice, selectedCard)
        .then((response: IMarketCard | undefined) => {
          if (response) {
            const cardsCopy = cardData;
            const cardIndex: number = cardsCopy.findIndex(
              (c) => c.id === response.id,
            );
            cardsCopy.splice(cardIndex, 1);
            setCardData(cardsCopy);
          }
        })
        .catch((error) => {
          console.error(error);
        });
    }
  };

  const getCardData = () => {
    if (user) {
      getUserCardCollection(user.id)
        .then((response: IUser | undefined) => {
          if (response) {
            setCardData(response.cards);
          }
        })
        .catch((error) => {
          console.error(error);
        });
    }
  };

  useEffect(() => {
    getCardData();
  }, []);

  return (
    <Container className="mt-5">
      <Box className="flex justify-center items-center mb-5">
        <Typography variant="h4" component="h1" className="mr-2">
          Vente
        </Typography>
        <Typography variant="subtitle1" component="p" className="italic">
          ({cardData.length} cartes dans votre collection)
        </Typography>
      </Box>
      <Box sx={{ display: "flex" }}>
        <Box sx={{ flexGrow: 1, mr: selectedCard ? 2 : 0 }}>
          <TableContainer component={Paper}>
            <Table
              sx={{ minWidth: 650 }}
              aria-label="simple table"
              size="small"
            >
              <TableHead>
                <TableRow sx={{ backgroundColor: "#2196f3" }}>
                  <TableCell className="text-white">Nom</TableCell>
                  <TableCell align="right" className="text-white">
                    Famille
                  </TableCell>
                  <TableCell align="right" className="text-white">
                    Affinité
                  </TableCell>
                  <TableCell align="center" className="text-white">
                    Energie
                  </TableCell>
                  <TableCell align="center" className="text-white">
                    Vie
                  </TableCell>
                  <TableCell align="center" className="text-white">
                    Attaque
                  </TableCell>
                  <TableCell align="center" className="text-white">
                    Défense
                  </TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {paginatedData.map((card) => (
                  <TableRow
                    key={card.id}
                    hover
                    onClick={() => handleRowClick(card)}
                    sx={{ cursor: "pointer" }}
                  >
                    <TableCell component="th" scope="row">
                      {card.name}
                    </TableCell>
                    <TableCell align="right">{card.family}</TableCell>
                    <TableCell align="right">{card.affinity}</TableCell>
                    <TableCell align="center">{card.energy}</TableCell>
                    <TableCell align="center">{card.health}</TableCell>
                    <TableCell align="center">{card.attack}</TableCell>
                    <TableCell align="center">{card.defence}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
            <TablePagination
              rowsPerPageOptions={[5, 10, 25]}
              component="div"
              count={cardData.length}
              rowsPerPage={rowsPerPage}
              page={page}
              onPageChange={handleChangePage}
              onRowsPerPageChange={handleChangeRowsPerPage}
            />
          </TableContainer>
        </Box>
        {selectedCard && (
          <Box sx={{ width: "300px" }}>
            <Card
              className="pt-3 pb-0"
              sx={{
                width: "100%",
                height: 420,
                display: "flex",
                flexDirection: "column",
              }}
            >
              <CardMedia
                component="img"
                sx={{ height: 140, width: "100%", objectFit: "contain" }}
                image={selectedCard.imageUrl}
                alt={selectedCard.name}
              />

              <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                  {selectedCard.name}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  {selectedCard.description}
                </Typography>
                <Typography
                  variant="body2"
                  color="text.secondary"
                  className="mt-2"
                >
                  <strong>Family:</strong> {selectedCard.family}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  <strong>Affinity:</strong> {selectedCard.affinity}
                </Typography>
                <Grid container spacing={0} className="mt-2">
                  <Grid item xs={6}>
                    <div className="flex items-center">
                      <OfflineBoltRoundedIcon className="mr-1" />
                      <Typography variant="body2" color="text.secondary">
                        <strong>Energy:</strong> {selectedCard.energy}
                      </Typography>
                    </div>
                  </Grid>
                  <Grid item xs={6}>
                    <div className="flex items-center">
                      <FavoriteRoundedIcon className="mr-1" />
                      <Typography variant="body2" color="text.secondary">
                        <strong>Health:</strong> {selectedCard.health}
                      </Typography>
                    </div>
                  </Grid>
                  <Grid item xs={6}>
                    <div className="flex items-center">
                      <SportsMmaRoundedIcon className="mr-1" />
                      <Typography variant="body2" color="text.secondary">
                        <strong>Attack:</strong> {selectedCard.attack}
                      </Typography>
                    </div>
                  </Grid>
                  <Grid item xs={6}>
                    <div className="flex items-center">
                      <ShieldRoundedIcon className="mr-1" />
                      <Typography variant="body2" color="text.secondary">
                        <strong>Defence:</strong> {selectedCard.defence}
                      </Typography>
                    </div>
                  </Grid>
                </Grid>
              </CardContent>
              <CardContent
                className="pt-0"
                style={{ display: "flex", alignItems: "center" }}
              >
                <TextField
                  size="small"
                  type="number"
                  label="Prix"
                  variant="outlined"
                  value={sellingPrice}
                  onChange={handlePriceChange}
                  InputProps={{
                    endAdornment: (
                      <InputAdornment position="end">€</InputAdornment>
                    ),
                    inputProps: { min: 0.01, step: 0.01 },
                  }}
                  style={{ marginRight: "16px" }}
                />
                <Button
                  fullWidth
                  variant="contained"
                  startIcon={<SellIcon />}
                  disabled={!sellingPrice || Number(sellingPrice) <= 0}
                  onClick={sellSelectedCard}
                >
                  Vendre
                </Button>
              </CardContent>
            </Card>
          </Box>
        )}
      </Box>
    </Container>
  );
}
