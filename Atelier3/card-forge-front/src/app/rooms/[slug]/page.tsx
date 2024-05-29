"use client";

import { getUserCardCollection } from "@/api/card";
import { ICard } from "@/types/Card";
import { useParams } from "next/navigation";
import { useState } from "react";
import Grid from "@mui/material/Grid";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import { Button, CircularProgress, Container } from "@mui/material";
import OfflineBoltRoundedIcon from "@mui/icons-material/OfflineBoltRounded";
import FavoriteRoundedIcon from "@mui/icons-material/FavoriteRounded";
import ShieldRoundedIcon from "@mui/icons-material/ShieldRounded";
import SportsMmaRoundedIcon from "@mui/icons-material/SportsMmaRounded";
import { ArenaViews } from "@/types/Enum";

export default function Arena() {
  const params = useParams();
  const [view, setView] = useState<ArenaViews>(ArenaViews.Loading);
  const [cardData, setCardData] = useState<ICard[]>([]);
  const [selectedCard, setSelectedCard] = useState<ICard | null>(null);

  getUserCardCollection()
    .then((response: ICard[] | undefined) => {
      if (response) {
        setCardData(response);
      }
    })
    .catch((error) => {
      console.error(error);
    });

  const handleCardClick = (card: ICard) => {
    setSelectedCard(card);
  };

  const handleConfirmClick = () => {
    // Do something...
  };

  return (
    <Container className="mt-5">
      {view === ArenaViews.Loading && (
        <Box className="flex justify-center items-center flex-col">
          <CircularProgress />
          <Typography variant="h6" className="mt-3">
            Chargement du salon...
          </Typography>
        </Box>
      )}

      {view === ArenaViews.CardSelection && (
        <>
          <Box className="flex justify-center items-center mb-5">
            <Typography variant="h4" component="h1" className="mr-2">
              Collection
            </Typography>
            <Typography variant="subtitle1" component="p" className="italic">
              ({cardData.length} éléments)
            </Typography>
          </Box>
          <Grid container spacing={1}>
            {cardData.map((card) => (
              <Grid item key={card.id} xs={12} sm={6} md={3}>
                <Card
                  className={`pt-3 pb-0 ${selectedCard?.id === card.id ? "border-4 border-blue-500" : ""}`}
                  onClick={() => handleCardClick(card)}
                  style={{ cursor: "pointer" }}
                >
                  <CardMedia
                    component="img"
                    sx={{ height: 140, width: "100%", objectFit: "contain" }}
                    image={card.imageUrl}
                    alt={card.name}
                  />
                  <CardContent>
                    <Typography
                      gutterBottom
                      variant="h5"
                      component="div"
                      className="text-center"
                    >
                      {card.name}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      {card.description}
                    </Typography>
                    <Typography
                      variant="body2"
                      color="text.secondary"
                      className="mt-2"
                    >
                      <strong>Family:</strong> {card.family}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      <strong>Affinity:</strong> {card.affinity}
                    </Typography>
                    <Grid container spacing={0} className="mt-2">
                      <Grid item xs={6}>
                        <div className="flex items-center">
                          <OfflineBoltRoundedIcon className="mr-1" />
                          <Typography variant="body2" color="text.secondary">
                            <strong>Energy:</strong> {card.energy}
                          </Typography>
                        </div>
                      </Grid>
                      <Grid item xs={6}>
                        <div className="flex items-center">
                          <FavoriteRoundedIcon className="mr-1" />
                          <Typography variant="body2" color="text.secondary">
                            <strong>Health:</strong> {card.health}
                          </Typography>
                        </div>
                      </Grid>
                      <Grid item xs={6}>
                        <div className="flex items-center">
                          <SportsMmaRoundedIcon className="mr-1" />
                          <Typography variant="body2" color="text.secondary">
                            <strong>Attack:</strong> {card.attack}
                          </Typography>
                        </div>
                      </Grid>
                      <Grid item xs={6}>
                        <div className="flex items-center">
                          <ShieldRoundedIcon className="mr-1" />
                          <Typography variant="body2" color="text.secondary">
                            <strong>Defence:</strong> {card.defence}
                          </Typography>
                        </div>
                      </Grid>
                    </Grid>
                  </CardContent>
                </Card>
              </Grid>
            ))}
          </Grid>
          <Box className="flex justify-center items-center mt-5">
            <Button
              variant="contained"
              color="primary"
              disabled={!selectedCard}
              onClick={handleConfirmClick}
            >
              Confirmer la sélection
            </Button>
          </Box>
        </>
      )}
    </Container>
  );
}
