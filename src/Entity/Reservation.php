<?php

namespace App\Entity;

use App\Repository\ReservationRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity(repositoryClass=ReservationRepository::class)
 */
class Reservation
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\ManyToOne(targetEntity=Chambre::class, inversedBy="reservations")
     */
    private $chambre;

    /**
     * @ORM\ManyToOne(targetEntity=Utilisateur::class, inversedBy="reservations")
     */
    private $utilisateur;

    /**
     * @ORM\Column(type="integer")
     * @Assert\NotBlank(message="le champ est obligatoire")
     */
    private $quantite;

    /**
     * @ORM\Column(type="datetime" , options={"default": "CURRENT_TIMESTAMP"})
     * @Assert\NotBlank(message="le champ est obligatoire")
     * @Assert\GreaterThan("Today",message="Saisir la date à partir de la date d'aujourd'hui")
     */
    private $datedebut;

    /**
     * @ORM\Column(type="datetime" , options={"default": "CURRENT_TIMESTAMP"})
     * @Assert\NotBlank(message="le champ est obligatoire")
     */
    private $datefin;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getChambre(): ?Chambre
    {
        return $this->chambre;
    }

    public function setChambre(?Chambre $chambre): self
    {
        $this->chambre = $chambre;

        return $this;
    }

    public function getUtilisateur(): ?Utilisateur
    {
        return $this->utilisateur;
    }

    public function setUtilisateur(?Utilisateur $utilisateur): self
    {
        $this->utilisateur = $utilisateur;

        return $this;
    }

    public function getQuantite(): ?int
    {
        return $this->quantite;
    }

    public function setQuantite(int $quantite): self
    {
        $this->quantite = $quantite;

        return $this;
    }

    public function getDatedebut(): ?\DateTimeInterface
    {
        return $this->datedebut;
    }

    public function setDatedebut(\DateTimeInterface $datedebut): self
    {
        $this->datedebut = $datedebut;

        return $this;
    }

    public function getDatefin(): ?\DateTimeInterface
    {
        return $this->datefin;
    }

    public function setDatefin(\DateTimeInterface $datefin): self
    {
        $this->datefin = $datefin;

        return $this;
    }
}
