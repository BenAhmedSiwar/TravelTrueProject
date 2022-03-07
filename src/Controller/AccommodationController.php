<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class AccommodationController extends AbstractController
{
    /**
     * @Route("/accommodation", name="accommodation")
     */
    public function index(): Response
    {
        return $this->render('accommodation/index.html.twig', [
            'controller_name' => 'AccommodationController',
        ]);
    }
}
