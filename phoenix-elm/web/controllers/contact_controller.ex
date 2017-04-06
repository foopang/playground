defmodule PhoenixElm.ContactController do
  use PhoenixElm.Web, :controller

  alias PhoenixElm.Contact

  def index(conn, params) do
    page = Contact
      |> order_by(:first_name)
      |> Repo.paginate(params)

    render conn, page: page
  end
end
