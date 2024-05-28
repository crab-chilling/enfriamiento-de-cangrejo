"use client";

import * as React from "react";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import Link from "next/link";
import CollectionsBookmarkIcon from "@mui/icons-material/CollectionsBookmark";
import LocalGroceryStoreIcon from "@mui/icons-material/LocalGroceryStore";
import SellIcon from "@mui/icons-material/Sell";
import SportsEsportsIcon from "@mui/icons-material/SportsEsports";

const cardsData = [
  {
    id: 1,
    title: "Collection",
    icon: (
      <CollectionsBookmarkIcon
        sx={{ display: { xs: "none", md: "flex" }, mr: 1 }}
      />
    ),
    description: "Consultez les cartes de votre collection",
    link: "/collection",
  },
  {
    id: 2,
    title: "Achat",
    icon: (
      <LocalGroceryStoreIcon
        sx={{ display: { xs: "none", md: "flex" }, mr: 1 }}
      />
    ),
    description:
      "Achetez de nouvelles cartes et ajouter-les à votre collection",
    link: "/buy",
  },
  {
    id: 3,
    title: "Vente",
    icon: <SellIcon sx={{ display: { xs: "none", md: "flex" }, mr: 1 }} />,
    description: "Vendez les cartes que vous ne souhaitez plus utiliser",
    link: "/sell",
  },
  {
    id: 4,
    title: "Combat",
    icon: (
      <SportsEsportsIcon sx={{ display: { xs: "none", md: "flex" }, mr: 1 }} />
    ),
    description: "Combattez en utilisant les cartes de votre collection",
    link: "/fight",
  },
];

export default function Home() {
  return (
    <main className="flex flex-col items-center p-24">
      <Typography variant="h3" component="div" gutterBottom>
        Bienvenue sur Card Forge !
      </Typography>
      <div className="flex flex-row w-full justify-center mt-5">
        <hr className="w-1/4 border-t-2 border-black" />
      </div>
      <div className="flex flex-row flex-wrap justify-center gap-5 mt-10">
        {cardsData.map((card) => (
          <div key={card.id} style={{ width: "300px", height: "400px" }}>
            <Card sx={{ minWidth: 275 }}>
              <CardContent>
                <div className="flex flex-row items-center">
                  {card.icon}
                  <Typography variant="h5" component="div">
                    {card.title}
                  </Typography>
                </div>
                <Typography sx={{ mb: 1.5, mt: 2 }} color="text.secondary">
                  {card.description}
                </Typography>
              </CardContent>
              <CardActions>
                <Link href={card.link} passHref>
                  <Button size="small">Accéder</Button>
                </Link>
              </CardActions>
            </Card>
          </div>
        ))}
      </div>
    </main>
  );
}
