"use client";

import React, { useState, useEffect } from "react";
import Grid from "@mui/material/Grid";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import { ICard } from "@/types/Card";
import { Container } from "@mui/material";
import OfflineBoltRoundedIcon from "@mui/icons-material/OfflineBoltRounded";
import FavoriteRoundedIcon from "@mui/icons-material/FavoriteRounded";
import ShieldRoundedIcon from "@mui/icons-material/ShieldRounded";
import SportsMmaRoundedIcon from "@mui/icons-material/SportsMmaRounded";
import { useAuth } from "@/providers/AuthProvider";

export default function Collection() {
  const { userContext } = useAuth();
  const [cardData, setCardData] = useState<ICard[]>([]);

  useEffect(() => {
    if (userContext) {
      setCardData(userContext.cards);
    }
  }, [userContext]);

  return (
    <Container className="mt-5">
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
            <Card className="pt-3 pb-0">
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
    </Container>
  );
}
