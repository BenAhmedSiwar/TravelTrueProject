<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class GlobalController extends AbstractController
{
    /**
     * @Route("/", name="global")
     */
    public function index(): Response
    {
        return $this->render('global/index.html.twig');
    }

    /**
     * @Route("/admin", name="global_admin")
     */
    public function index_admin(): Response
    {
        return $this->render('global/admin.html.twig');
    }
}
