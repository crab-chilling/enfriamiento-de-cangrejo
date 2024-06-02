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
import { ICard, ICardWithOnSaleStatus } from "@/types/Card";
import { IMarketCard } from "@/types/Market";
import { TextField, InputAdornment } from "@mui/material";
import OfflineBoltRoundedIcon from "@mui/icons-material/OfflineBoltRounded";
import FavoriteRoundedIcon from "@mui/icons-material/FavoriteRounded";
import ShieldRoundedIcon from "@mui/icons-material/ShieldRounded";
import SportsMmaRoundedIcon from "@mui/icons-material/SportsMmaRounded";
import { getUserOnSaleCardCollection, sell } from "@/api/market";
import { toast } from "react-toastify";
import { useAuth } from "@/providers/AuthProvider";

export default function SellTable() {
  const [page, setPage] = useState(0);
  const [cardData, setCardData] = useState<ICardWithOnSaleStatus[]>([]);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [selectedCard, setSelectedCard] =
    useState<ICardWithOnSaleStatus | null>(null);
  const [sellingPrice, setSellingPrice] = useState<number | null>(null);
  const { userContext } = useAuth();

  const handlePriceChange = (event: any) => {
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

  const handleRowClick = (card: ICardWithOnSaleStatus) => {
    setSelectedCard(card);
  };

  const paginatedData = cardData.slice(
    page * rowsPerPage,
    page * rowsPerPage + rowsPerPage,
  );

  const sellSelectedCard = () => {
    if (sellingPrice && selectedCard) {
      sell({ card: selectedCard, price: sellingPrice })
        .then((response: IMarketCard | undefined) => {
          if (response) {
            toast.success("Votre annonce a bien été publiée !");
            const copy = [...cardData];
            const index = copy.findIndex((c) => c.id === selectedCard.id);
            if (index !== -1) {
              copy[index].isOnSale = true;
            }
            setCardData(copy);
            setSelectedCard(null);
          }
        })
        .catch((error) => {
          console.error(error);
          toast.error(
            "Une erreur est survenue lors de la mise en ligne de votre annonce.",
          );
        });
    }
  };

  useEffect(() => {
    const fetchCardData = async () => {
      try {
        if (userContext) {
          const userCards = userContext.cards;
          const onSaleCards = await getUserOnSaleCardCollection(userContext.id);
          if (onSaleCards) {
            const onSaleCardIds = new Set(onSaleCards.map((card) => card.id));
            const combinedCards = userCards.map((card) => ({
              ...card,
              isOnSale: onSaleCardIds.has(card.id),
            }));
            setCardData(combinedCards);
          }
        }
      } catch (error) {
        console.error(error);
      }
    };

    if (userContext) {
      fetchCardData();
    }
  }, [userContext]);

  return (
    <Container className="mt-5">
      <Box className="flex justify-center items-center mb-5">
        <Typography variant="h4" component="h1" className="mr-2">
          Vendre une carte
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
                  <TableCell align="center" className="text-white">
                    En vente
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
                    <TableCell align="center">
                      {card.isOnSale ? "Oui" : "Non"}
                    </TableCell>
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
                {!selectedCard.isOnSale && (
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
                )}
                <Button
                  fullWidth
                  variant="contained"
                  startIcon={<SellIcon />}
                  disabled={
                    !sellingPrice ||
                    Number(sellingPrice) <= 0 ||
                    selectedCard.isOnSale
                  }
                  onClick={sellSelectedCard}
                >
                  {selectedCard.isOnSale ? "En vente" : "Vendre"}
                </Button>
              </CardContent>
            </Card>
          </Box>
        )}
      </Box>
    </Container>
  );
}
